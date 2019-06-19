package ru.kolesnikov.sm_center.savedata;

import android.content.Context;
import android.content.SharedPreferences;

import static ru.kolesnikov.sm_center.Constants.Constant.*;


public class SharedPreferencesSave implements ISaveData {


    private SharedPreferences mSettings;

    public SharedPreferencesSave(Context context) {
        this.mSettings =  context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public void save(String name, String value) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(name, value);
        editor.apply();
    }

    @Override
    public String read(String name) {
        if(mSettings.contains(name)) {
            String response = mSettings.getString(name, "");
            return  response.replaceAll(SEMICOLON, NEW_STRING);
        }
        return null;
    }
}
