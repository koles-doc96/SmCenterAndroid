package ru.kolesnikov.sm_center;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.logging.Logger;

import br.com.sapereaude.maskedEditText.MaskedEditText;
import ru.kolesnikov.sm_center.passworMask.AsteriskPasswordTransformationMethod;


public class MainActivity extends AppCompatActivity {

    public static final String INCORECT_PASSWORD = "2";
    public static final String INCORRECT_VALUES = "1";
    public static final String AMPERSAND = "&";
    public static final String EQUALITY = "=";
    private MaskedEditText phone;
    private EditText pwd;
    private Toast toast;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_RESPONSE = "Response";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phone = findViewById(R.id.phone);
        pwd = findViewById(R.id.pwd);
        pwd.setTransformationMethod(new AsteriskPasswordTransformationMethod());


    }

    public void login(View view) {
        String phoneNumber = phone.getRawText();
        String password = pwd.getText().toString();
        if (StringUtils.isNotEmpty(phoneNumber) && StringUtils.isNotEmpty(password)) {
            RequestConnection requestConnection = new RequestConnection();
            requestConnection.execute(phoneNumber, password);
        } else {
            toast = Toast.makeText(getApplicationContext(),
                    R.string.warningTost, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    class RequestConnection extends AsyncTask<String, String, String> {

        private SharedPreferences mSettings;
        private Logger log = Logger.getLogger(RequestConnection.class.getName());

        @Override
        protected String doInBackground(String... strings) {
            try {
                DefaultHttpClient hc = new DefaultHttpClient();
                ResponseHandler<String> res = new BasicResponseHandler();
                HttpGet httpGet = new HttpGet(getString(R.string.requestUrl)
                        + getString(R.string.phoneParam) + EQUALITY + getString(R.string.rusCode) + strings[0]
                        + AMPERSAND + getString(R.string.passwordParam) + EQUALITY + strings[1]);
                log.info("Request Url: " + httpGet.getRequestLine().getUri());
                String response = hc.execute(httpGet, res);
                log.info("Response: " + response);
                if (StringUtils.isNotEmpty(response)
                        && !INCORRECT_VALUES.contains(response)
                        && !INCORECT_PASSWORD.contains(response)) {
                    mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putString(APP_PREFERENCES_RESPONSE, response);
                    editor.apply();

                    Intent questionIntent = new Intent(MainActivity.this,
                            ResultActivity.class);
                    startActivityForResult(questionIntent, 1);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    return response;
                } else if (INCORECT_PASSWORD.contains(response)) {
                    showToast(getString(R.string.incorrectPassword));
                } else {
                    showToast(getString(R.string.incorrectValues));
                }
            } catch (IOException e) {
                log.warning(e.getMessage());
            }
            return null;
        }

        private void showToast(final String text) {
            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
                }
            });
        }
    }


}
