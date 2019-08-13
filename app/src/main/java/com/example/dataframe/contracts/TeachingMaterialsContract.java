package com.example.dataframe.contracts;

import android.provider.BaseColumns;

public class TeachingMaterialsContract {

    private TeachingMaterialsContract(){}

    public static class Constants implements BaseColumns{

        public static final String TABLE_NAME = "teaching_materials_table";

        //columns
        public static final String SUBJECT = "subject";
        public static final String TEXT_BOOK = "text_book";
        public static final String TEACHING_GUIDE = "teaching_guide";
        public static final String CLASS_PERIOD = "class_period";
    }

    public static final String CREATE_ENTRIES = "CREATE TABLE " + Constants.TABLE_NAME + " ( " +
                                                Constants._ID + " INTEGER PRIMARY KEY, " +
                                                Constants.SUBJECT + " TEXT, " +
                                                Constants.TEXT_BOOK + "INTEGER, " +
                                                Constants.TEACHING_GUIDE + "INTEGER, " +
                                                Constants.CLASS_PERIOD + "INTEGER )";

    public static final String DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Constants.TABLE_NAME;

}
