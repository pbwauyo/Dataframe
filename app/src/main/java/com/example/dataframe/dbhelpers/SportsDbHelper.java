package com.example.dataframe.dbhelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.dataframe.contracts.SportsContract.CREATE_ENTRIES;
import static com.example.dataframe.contracts.SportsContract.DELETE_ENTRIES;

public class SportsDbHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "SportsDatabase.db";
    static final int DATABASE_VERSION = 1;

    public SportsDbHelper(Context context){
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

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
