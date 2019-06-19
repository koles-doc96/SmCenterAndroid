package ru.kolesnikov.sm_center;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import ru.kolesnikov.sm_center.savedata.DataReadOrSave;
import ru.kolesnikov.sm_center.savedata.SharedPreferencesSave;
import ru.kolesnikov.sm_center.savedata.SqlSave;

import static ru.kolesnikov.sm_center.Constants.Constant.*;


public class ResultActivity extends Activity {
    private DataReadOrSave readOrSave;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        result = findViewById(R.id.result);
        Intent intent = getIntent();

        String phone = intent.getStringExtra(APP_PREFERENCES_PHONE);
        setReadOrSave(intent.getStringExtra(SAVE_CLASS_NAME));
        result.setText(readOrSave != null ? readOrSave.read(phone) : "");

    }

    public void setReadOrSave(String className) {
        if (SharedPreferencesSave.class.getSimpleName().contains(className)) {
            readOrSave = new DataReadOrSave(new SharedPreferencesSave(getApplicationContext()));
        } else if (SqlSave.class.getSimpleName().contains(className)) {
            readOrSave = new DataReadOrSave(new SqlSave(getApplicationContext()));
        }
    }
}
