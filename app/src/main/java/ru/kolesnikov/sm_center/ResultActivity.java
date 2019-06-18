package ru.kolesnikov.sm_center;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import static ru.kolesnikov.sm_center.MainActivity.APP_PREFERENCES;
import static ru.kolesnikov.sm_center.MainActivity.APP_PREFERENCES_RESPONSE;

public class ResultActivity extends Activity {
    public static final String SEMICOLON = ";";
    public static final String NEW_STRING = "\n";
    private TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        result = findViewById(R.id.result);
        SharedPreferences mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if(mSettings.contains(APP_PREFERENCES_RESPONSE)) {
            String responce = mSettings.getString(APP_PREFERENCES_RESPONSE, "");
            responce= responce.replaceAll(SEMICOLON, NEW_STRING);
            result.setText(responce);

        }
    }
}
