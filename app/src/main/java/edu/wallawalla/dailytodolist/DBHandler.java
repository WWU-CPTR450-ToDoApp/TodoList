package edu.wallawalla.dailytodolist;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "todotaskDB.db";
    private static final String TABLE_TASKS = "tasks";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TASKNAME = "taskname";
    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_TASKNAME + " TEXT" + ")";
        db.execSQL(CREATE_TASKS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    public void addTask(ToDoTask task) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASKNAME, task.getTaskName());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    public ToDoTask findTask(String taskname) {
        String query = "Select * FROM " + TABLE_TASKS + " WHERE " + COLUMN_TASKNAME + " = \"" + taskname + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ToDoTask task = new ToDoTask();
        if(cursor.moveToFirst()) {
            cursor.moveToFirst();
            task.setID(Integer.parseInt(cursor.getString(0)));
            task.setTaskName(cursor.getString(1));
            cursor.close();
        } else {
            task = null;
        }
        db.close();
        return task;
    }

    public boolean deleteTask(String taskname) {
        boolean result = false;
        String query = "Select * FROM " + TABLE_TASKS + " WHERE " + COLUMN_TASKNAME + " = \"" + taskname + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ToDoTask task = new ToDoTask();
        if(cursor.moveToFirst()) {
            task.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_TASKS, COLUMN_ID + " = ?",
                    new String[]{ String.valueOf(task.getID()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
}
