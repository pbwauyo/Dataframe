package com.example.dataframe.contracts;

import android.provider.BaseColumns;

public class NonTeachingStaffContract {

    public static class Constants implements BaseColumns{
        public static final String TABLE_NAME = "non_teaching_staff_table";

        //fields
        public static final String MALE_CLEANERS = "male_cleaners";
        public static final String FEMALE_CLEANERS = "female_cleaners";
        public static final String MALE_SECURITY = "male_security";
        public static final String FEMALE_SECURITY = "female_security";
        public static final String MALE_COOKS = "male_cooks";
        public static final String FEMALE_COOKS = "female_cooks";
        public static final String MALE_LIBRARIANS = "male_librarians";
        public static final String FEMALE_LIBRARIANS = "female_librarians";
    }

    public static final String CREATE_ENTRIES = "CREATE TABLE " + Constants.TABLE_NAME + " (" +
                                                Constants._ID + " INTEGER PRIMARY KEY, " +
                                                Constants.MALE_CLEANERS + " INTEGER, " +
                                                Constants.FEMALE_CLEANERS + " INTEGER, " +
                                                Constants.MALE_SECURITY + " INTEGER, " +
                                                Constants.FEMALE_SECURITY + " INTEGER, " +
                                                Constants.MALE_COOKS + " INTEGER, " +
                                                Constants.FEMALE_COOKS + " INTEGER, " +
                                                Constants.MALE_LIBRARIANS + " INTEGER, " +
                                                Constants.FEMALE_LIBRARIANS + " INTEGER)";

    public static final String DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Constants.TABLE_NAME;
}
