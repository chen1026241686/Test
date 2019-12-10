package com.example.customview;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Looper;
import android.util.Log;

public class MySqliteOpenHelper extends SQLiteOpenHelper {
    public MySqliteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MySqliteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public MySqliteOpenHelper(Context context, String name, int version, SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    private String sql = "CREATE TABLE Persons\n" + "(\n" + "id int,\n" + "LastName varchar(255),\n" + "FirstName varchar(255),\n" + "Address varchar(255),\n" + "City varchar(255)\n" + ")";

    private String insert1 = "INSERT INTO Persons(lastname,firstname,address,city) VALUES ('Gates', 'Bill', 'Xuanwumen 10', 'Beijing')";

    @Override
    public void onCreate(SQLiteDatabase db) {

        Looper looper;

        Log.e("FFF", "SqliteOpenHelper----->onCreate");
        db.execSQL(sql);
        db.execSQL(insert1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
