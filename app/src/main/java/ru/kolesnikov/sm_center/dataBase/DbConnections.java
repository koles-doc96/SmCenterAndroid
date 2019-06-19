package ru.kolesnikov.sm_center.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DbConnections {
    private static SQLiteDatabase conn;

    private DbConnections() {
    }

    public static SQLiteDatabase getConnections(Context context){
        if(conn == null){
            conn = new DbHelper(context).getWritableDatabase();
        }
        return conn;
    }

}
