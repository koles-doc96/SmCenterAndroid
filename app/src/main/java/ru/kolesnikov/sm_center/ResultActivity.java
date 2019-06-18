package ru.kolesnikov.sm_center;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import static ru.kolesnikov.sm_center.MainActivity.APP_PREFERENCES;
import static ru.kolesnikov.sm_center.MainActivity.APP_PREFERENCES_RESPONSE;

public class ResultActivity extends AppCompatActivity {
    private SharedPreferences mSettings;
    private TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        result = findViewById(R.id.result);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if(mSettings.contains(APP_PREFERENCES_RESPONSE)) {
            String responce =mSettings.getString(APP_PREFERENCES_RESPONSE, "");
            responce= responce.replaceAll(";","\n");
            result.setText(responce);
        }
    }
}
