package com.example.dataframe.contracts;

import android.provider.BaseColumns;

public class HIVContract {

    public HIVContract(){}

    public static class Constants implements BaseColumns {

        public static final String TABLE_NAME = "HIV_AIDS_TABLE";

        //columns
        public static final String MALES = "males";
        public static final String FEMALES = "females";
        public static final String INFORMATION_DISSEMINATION = "information_dissemination_methods";
    }

    public static final String CREATE_ENTRIES = "CREATE TABLE " + Constants.TABLE_NAME + " (" +
                                                 Constants._ID + " INTEGER PRIMARY KEY, " +
                                                 Constants.MALES + " INTEGER, " +
                                                 Constants.FEMALES + " INTEGER, " +
                                                 Constants.INFORMATION_DISSEMINATION + " TEXT)";

    public static final String DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Constants.TABLE_NAME;
}
