package com.example.dataframe.contracts;

import android.provider.BaseColumns;

public class InfrastructureContract {

    private InfrastructureContract(){}

    public static class Constants implements BaseColumns {
        public static final String TABLE_NAME = "infrastructure_table";

        //fields
        public static final String INFRASTRUCTURE_NAME = "infrastructure_name";
        public static final String INFRASTRUCTURE_NUMBER = "infrastructure_number";
        public static final String INFRASTRUCTURE_TYPE = "infrastructure_type";
        public static final String COMPLETION_STATUS = "completion_status";
        public static final String INUSE = "in_use";
        public static final String EMPLOYEES_TOTAL = "employees_total";
    }

    public static final String CREATE_ENTRIES = "CREATE TABLE " + Constants.TABLE_NAME + " (" +
                                                Constants._ID + " INTEGER PRIMARY KEY, " +
                                                Constants.INFRASTRUCTURE_NAME + " TEXT, " +
                                                Constants.INFRASTRUCTURE_NUMBER + " INTEGER, " +
                                                Constants.INFRASTRUCTURE_TYPE + " TEXT, " +
                                                Constants.COMPLETION_STATUS + " TEXT, " +
                                                Constants.INUSE + " TEXT, " +
                                                Constants.EMPLOYEES_TOTAL + " TEXT)";

    public static final String DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Constants.TABLE_NAME;

}
