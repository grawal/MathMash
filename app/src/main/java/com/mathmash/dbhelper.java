package com.mathmash;

import android.provider.BaseColumns;

/**
 * Created by Home on 9/13/2016.
 */
public final class dbhelper {
    public static final String DATABASE_NAME = "MathMashdb";
    public static final Integer DATABASE_VERSION = 3;

    public void dbhelper(){}

    public static abstract class stats implements BaseColumns {
        public static final String TABLE_NAME = "stats";
        public static final String COLUMN_ID = "_ID";
        public static final String COLUMN_NUM1 = "num1";
        public static final String COLUMN_NUM2 = "num2";
        public static final String COLUMN_OPERATION = "operation";
        public static final String COLUMN_ANS = "ans";
        public static final String COLUMN_ISCORRECT = "isCorrect";
        public static final String COLUMN_DATETIME = "datetime";
        public static final String CREATE_TABLE_STATS = "CREATE TABLE " + TABLE_NAME + "("
                +COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +COLUMN_NUM1 + " INTEGER, "
                +COLUMN_NUM2 + " INTEGER, "
                +COLUMN_OPERATION + " STRING, "
                +COLUMN_ANS + " INTEGER, "
                +COLUMN_ISCORRECT + " INTEGER, "
                +COLUMN_DATETIME + " STRING )";


    }


}
