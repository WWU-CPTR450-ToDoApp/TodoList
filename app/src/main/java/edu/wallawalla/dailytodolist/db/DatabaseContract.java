package edu.wallawalla.dailytodolist.db;

import android.provider.BaseColumns;

public final class DatabaseContract {
    public static final int     DATABASE_VERSION    = 1;
    public static final String  DATABASE_NAME       = "todotaskDB.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TASKNAME = "taskname";

    public static class Table1 implements BaseColumns {
        public static final String TABLE_NAME       = "taskTable";
        public static final String COLUMN_NAME_COL1 = "id";
        public static final String COLUMN_NAME_COL2 = "date";
        public static final String COLUMN_NAME_COL3 = "note";


        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME_COL1 + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_COL2 + TEXT_TYPE + COMMA_SEP +
                COLUMN_NAME_COL3 + TEXT_TYPE + " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
