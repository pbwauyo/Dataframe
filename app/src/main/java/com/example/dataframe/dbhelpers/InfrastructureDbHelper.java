package com.example.dataframe.dbhelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.dataframe.contracts.InfrastructureContract.CREATE_ENTRIES;
import static com.example.dataframe.contracts.InfrastructureContract.DELETE_ENTRIES;

public class InfrastructureDbHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "InfrastructureDatabase.db";
    static final int DATABASE_VERSION = 1;

    public InfrastructureDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_ENTRIES);
        onCreate(db);
    }
}
