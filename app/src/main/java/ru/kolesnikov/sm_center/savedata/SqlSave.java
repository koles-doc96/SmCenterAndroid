package ru.kolesnikov.sm_center.savedata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ru.kolesnikov.sm_center.dataBase.DbConnections;

import static ru.kolesnikov.sm_center.Constants.Constant.*;


public class SqlSave implements ISaveData {

    public static final int NOT_UNIQUE = -1;
    private Context context;

    public SqlSave(Context context) {
        this.context = context;
    }

    @Override
    public void save(String name, String value) {
        ContentValues cv = new ContentValues();
        cv.put(NAME, name);
        cv.put(VALUE, value);

        SQLiteDatabase database = DbConnections.getConnections(context);

        long rowID = database.insert(SAVE_TABLE, null, cv);
        Log.d(LOG_TAG, "row inserted, ID = " + rowID);
        if(rowID == NOT_UNIQUE){
            rowID = database.update(SAVE_TABLE,cv,NAME+" = ?",new String[]{name});
            Log.d(LOG_TAG, "updated rows count = " + rowID);
        }
    }

    @Override
    public String read(String name) {
        SQLiteDatabase database = DbConnections.getConnections(context);
        Cursor cursor;
        String sqlQuery = "Select " + VALUE + " from " + SAVE_TABLE + " where " + NAME + "=\'" + name+"\'";

        cursor = database.rawQuery(sqlQuery, null);
        String result = "";
        if (cursor != null && cursor.moveToFirst()) {
            do {
                for (String each :
                        cursor.getColumnNames()) {
                    result = cursor.getString(cursor.getColumnIndex(each));
                }
            } while (cursor.moveToNext());
        } else {
            return null;
        }

        return result.replaceAll(SEMICOLON, NEW_STRING);
    }
}
