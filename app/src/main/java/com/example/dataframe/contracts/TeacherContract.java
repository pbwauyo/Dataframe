package com.example.dataframe.contracts;

import android.provider.BaseColumns;

public class TeacherContract {

    private TeacherContract(){}

    public static class Constants implements BaseColumns{

        public static final String TABLE_NAME = "teacher_table";

        //fields
        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String SEX = "sex";
        public static final String NIN = "nin";
        public static final String EMPLOYEE_NUMBER = "employee_number";
        public static final String LICENSED = "licensed";
        public static final String PHONE_NUMBER = "phone_number";
        public static final String EMAIL_ADDRESS = "email_address";
        public static final String DATE_OF_BIRTH = "date_of_birth";
        public static final String QUALIFICATION = "qualification";
        public static final String DATE_OF_FIRST_POSTING = "date_first_posting";
        public static final String DATE_OF_FIRST_APPOINTMENT = "date_of_first_appointment";
        public static final String SALARY_SCALE = "salary_scale";
        public static final String MPS_NUMBER = "mps_number";
    }

    public static final String CREATE_ENTRIES = "CREATE TABLE " + Constants.TABLE_NAME + " (" +
                                                 Constants._ID + " INTEGER PRIMARY KEY, " +
                                                 Constants.FIRST_NAME + " TEXT, " +
                                                 Constants.LAST_NAME + " TEXT, " +
                                                 Constants.SEX + " TEXT, " +
                                                 Constants.NIN + " TEXT, " +
                                                 Constants.EMPLOYEE_NUMBER + " TEXT, " +
                                                 Constants.LICENSED + " TEXT, " +
                                                 Constants.PHONE_NUMBER + " TEXT, " +
                                                 Constants.EMAIL_ADDRESS + " TEXT, " +
                                                 Constants.DATE_OF_BIRTH + " TEXT, " +
                                                 Constants.QUALIFICATION + " TEXT, " +
                                                 Constants.DATE_OF_FIRST_POSTING + " TEXT, " +
                                                 Constants.DATE_OF_FIRST_APPOINTMENT + " TEXT, " +
                                                 Constants.SALARY_SCALE + " TEXT, " +
                                                 Constants.MPS_NUMBER + " TEXT)";

    public static final String DELETE_ENTRIES = "DROP TABLE IF EXISTS " + Constants.TABLE_NAME;
    }
