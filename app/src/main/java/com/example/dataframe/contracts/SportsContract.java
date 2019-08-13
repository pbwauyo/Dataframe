package com.example.dataframe.contracts;

import android.provider.BaseColumns;

public class SportsContract {
    public SportsContract(){}

    public static class Constants implements BaseColumns {
        public static final String TABLE_NAME = "sports_table";

        //columns
        public static final String SPORTS_EQUIPMENT = "sports_equipment";
        public static final String SPORTS_FACILITY = "sports_facility";
        public static final String SNE_SPORTS_EQUIPMENT = "sne_sports_equipment";
        public static final String EXTRA_CURRICULAR_ACTIVITIES_PARTICIPATION = "extracurricular_activities";
        public static final String EXPENDITURE = "expenditure";
    }

    public static final String CREATE_ENTRIES = "CREATE TABLE " + Constants.TABLE_NAME + " (" +
                                                 Constants._ID + " INTEGER PRIMARY KEY, " +
                                                 Constants.SPORTS_EQUIPMENT + " TEXT," +
                                                 Constants.SPORTS_FACILITY + " TEXT," +
                                                 Constants.SNE_SPORTS_EQUIPMENT + " TEXT,"+
                                                 Constants.EXTRA_CURRICULAR_ACTIVITIES_PARTICIPATION + " TEXT, " +
                                                 Constants.EXPENDITURE + " TEXT)";

    public static final String DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Constants.TABLE_NAME;

}
