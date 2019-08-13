package com.example.dataframe.contracts;

import android.provider.BaseColumns;

public class SchoolContract {
    private SchoolContract(){}

    public static class Constants implements BaseColumns{
        public static final String TABLE_NAME = "school_characteristics_table";

        public static final String SCHOOL_NAME = "school_name";
        public static final String SCHOOL_TYPE = "school_type";
        public static final String LOCATION = "location";
        public static final String EMIS_NUMBER = "emis_number";
        public static final String SCHOOL_LEVEL = "school_level";
        public static final String OWNERSHIP = "ownership";
        public static final String REGISTRATION_STATUS = "registration_status";
        public static final String FOUNDING_YEAR = "founding_year";
        public static final String FOUNDING_BODY = "founding_body";
        public static final String FUNDING_SOURCE = "funding_source";
    }

    public static final String CREATE_ENTRIES = "CREATE TABLE " + Constants.TABLE_NAME + " (" +
                                                Constants._ID + " INTEGER PRIMARY KEY, " +
                                                Constants.SCHOOL_NAME + " TEXT, " +
                                                Constants.SCHOOL_TYPE + " TEXT, " +
                                                Constants.LOCATION + " TEXT, " +
                                                Constants.EMIS_NUMBER + " TEXT, " +
                                                Constants.SCHOOL_LEVEL + " TEXT, " +
                                                Constants.OWNERSHIP + " TEXT, " +
                                                Constants.REGISTRATION_STATUS + " TEXT, " +
                                                Constants.FOUNDING_YEAR + " TEXT, " +
                                                Constants.FOUNDING_BODY + " TEXT, " +
                                                Constants.FUNDING_SOURCE + " TEXT )";

    public static final String DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Constants.TABLE_NAME;
}
