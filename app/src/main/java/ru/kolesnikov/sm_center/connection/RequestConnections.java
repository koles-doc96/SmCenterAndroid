package ru.kolesnikov.sm_center.connection;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.logging.Logger;

import ru.kolesnikov.sm_center.R;
import ru.kolesnikov.sm_center.ResultActivity;
import ru.kolesnikov.sm_center.savedata.DataReadOrSave;

import static ru.kolesnikov.sm_center.Constants.Constant.*;


public class RequestConnections extends AsyncTask<String, String, String> {

    private static final String INCORRECT_PASSWORD = "2";
    private static final String INCORRECT_VALUES = "1";
    private static final String AMPERSAND = "&";
    private static final String EQUALITY = "=";

    private static final int PHONE_INDEX = 0;
    private static final int PASSWORD_INDEX = 1;

    private Logger log = Logger.getLogger(RequestConnections.class.getName());
    private Context context;
    private DataReadOrSave readOrSave;

    public RequestConnections(Context context, DataReadOrSave readOrSave) {
        this.context = context;
        this.readOrSave = readOrSave;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            String phone = strings[PHONE_INDEX];
            String password = strings[PASSWORD_INDEX];
            DefaultHttpClient hc = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpGet httpGet = new HttpGet(context.getString(R.string.requestUrl)
                    + context.getString(R.string.phoneParam) + EQUALITY + context.getString(R.string.rusCode) + phone
                    + AMPERSAND + context.getString(R.string.passwordParam) + EQUALITY + password);
            log.info("Request Url: " + httpGet.getRequestLine().getUri());
            String response = hc.execute(httpGet, res);
            log.info("Response: " + response);
            if (StringUtils.isNotEmpty(response)
                    && !INCORRECT_VALUES.contains(response)
                    && !INCORRECT_PASSWORD.contains(response)) {
                readOrSave.save(phone, response);
                start(phone);
                return response;
            } else if (INCORRECT_PASSWORD.contains(response)) {
                log.warning(context.getString(R.string.incorrectPassword));
            } else {
                log.warning(context.getString(R.string.incorrectValues));
            }
        } catch (IOException e) {
            log.warning(e.getMessage());
        }
        return null;
    }

    private void start(String phone) {
        Intent intent = new Intent(context.getApplicationContext(), ResultActivity.class);
        intent.putExtra(APP_PREFERENCES_PHONE, phone);
        intent.putExtra(SAVE_CLASS_NAME, readOrSave.getSaveData().getClass().getSimpleName());
        context.startActivity(intent);
    }
}
