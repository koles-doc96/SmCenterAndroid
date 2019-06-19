package ru.kolesnikov.sm_center;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.github.pinball83.maskededittext.MaskedEditText;

import org.apache.commons.lang3.StringUtils;

import ru.kolesnikov.sm_center.connection.RequestConnections;
import ru.kolesnikov.sm_center.passworMask.AsteriskPasswordTransformationMethod;
import ru.kolesnikov.sm_center.savedata.DataReadOrSave;
import ru.kolesnikov.sm_center.savedata.SharedPreferencesSave;
import ru.kolesnikov.sm_center.savedata.SqlSave;


public class MainActivity extends Activity {

    private MaskedEditText phone;
    private EditText pwd;
    private Toast toast;
    private RadioButton radioButtonSharedPreferences;
    private RadioButton radioButtonSql;
    private DataReadOrSave readOrSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phone = findViewById(R.id.phone);
        pwd = findViewById(R.id.pwd);
        pwd.setTransformationMethod(new AsteriskPasswordTransformationMethod());

        radioButtonSharedPreferences = findViewById(R.id.radioButtonShared);
        radioButtonSharedPreferences.setOnClickListener(radioButtonClickListener);

        radioButtonSql = findViewById(R.id.radioButtonSql);
        radioButtonSql.setOnClickListener(radioButtonClickListener);

        readOrSave = new DataReadOrSave(new SharedPreferencesSave(getApplicationContext()));

    }

    public void login(View view) {
        String phoneNumber = phone.getUnmaskedText();
        String password = pwd.getText().toString();
        if (StringUtils.isNotEmpty(phoneNumber) && StringUtils.isNotEmpty(password)) {
            RequestConnections requestConnection = new RequestConnections(getApplicationContext(), readOrSave);
            requestConnection.execute(phoneNumber, password);
        } else {
            toast = Toast.makeText(getApplicationContext(),
                    R.string.warningTost, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RadioButton rb = (RadioButton) view;
            switch (rb.getId()) {
                case R.id.radioButtonShared:
                    readOrSave.setSaveData(new SharedPreferencesSave(getApplicationContext()));
                    break;
                case R.id.radioButtonSql:
                    readOrSave.setSaveData(new SqlSave(getApplicationContext()));
                    break;
            }
        }
    };

}
