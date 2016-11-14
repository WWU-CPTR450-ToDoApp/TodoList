package edu.wallawalla.dailytodolist;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import edu.wallawalla.dailytodolist.db.DBHandler;
import edu.wallawalla.dailytodolist.db.TaskContract;
import edu.wallawalla.dailytodolist.db.ToDoTask;
import edu.wallawalla.dailytodolist.fragments.AddTaskFragment;
import edu.wallawalla.dailytodolist.fragments.FindTaskFragment;

public class MainActivity extends AppCompatActivity {
    private DBHandler dbHandler;
    private ListView mTaskListView;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new DBHandler(this);
        mTaskListView = (ListView) findViewById(R.id.list_todo);
        updateUI();
    }


    // function that is called when the add button is clicked
    public void addTaskClicked(View view) {
        DialogFragment addfrag = new AddTaskFragment();
        addfrag.show(getSupportFragmentManager(), "addTask");
    }
    // called when the user clicks the add button on the alert popup
    public void addTaskMethod(String data_col1, long data_col2, long data_col3, int data_col4, int data_col5, String data_col6) {
        ToDoTask task = new ToDoTask(data_col1, data_col2, data_col3, data_col4, data_col5, data_col6);
        dbHandler.addTask(task);
        updateUI();
    }

    // function that is called when the find button is clicked
    public void findTaskClicked (View view) {
        DialogFragment findfrag = new FindTaskFragment();
        findfrag.show(getSupportFragmentManager(), "findTask");
    }
    // called when the user clicks the sort button on alert popup
    public void findTaskMethod(String sortBy) {
        String[] projection;      // the columns to return
        String   selection;       // the columns for the WHERE clause
        String[] selectionArgs;   // the values for the WHERE clause
        String   sortOrder;       // the sort order
        Cursor c = null;
        Calendar cal, calHi;
        switch(sortBy) {
            case "All": // ALL
                projection = new String[] {
                        TaskContract.TaskEntry._ID,
                        TaskContract.TaskEntry.COLUMN_NAME_COL1
                };
                selection = TaskContract.TaskEntry.COLUMN_NAME_COL1 + " LIKE '%'";
                selectionArgs = new String[] {};
                sortOrder = null;
                c = dbHandler.findTask(projection, selection, selectionArgs, sortOrder);
                break;
            case "Today": // TODAY
                projection = new String[] {
                        TaskContract.TaskEntry._ID,
                        TaskContract.TaskEntry.COLUMN_NAME_COL1
                };
                selection = TaskContract.TaskEntry.COLUMN_NAME_COL2 + " < ?";
                cal = Calendar.getInstance();
                cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)+1, 0, 0, 0);
                selectionArgs = new String[] {String.valueOf(cal.getTimeInMillis())};
                sortOrder = null;
                c = dbHandler.findTask(projection, selection, selectionArgs, sortOrder);
                break;
            case "Tomorrow": // TOMORROW
                projection = new String[] {
                        TaskContract.TaskEntry._ID,
                        TaskContract.TaskEntry.COLUMN_NAME_COL1
                };
                selection = TaskContract.TaskEntry.COLUMN_NAME_COL2 + " > ? "
                            + "AND " + TaskContract.TaskEntry.COLUMN_NAME_COL2 + " < ?";
                cal = Calendar.getInstance();
                cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)+1, 0, 0, 0);
                calHi = Calendar.getInstance();
                calHi.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)+2, 0, 0, 0);                selectionArgs = new String[] {
                        String.valueOf(cal.getTimeInMillis()),
                        String.valueOf(calHi.getTimeInMillis())};
                sortOrder = null;
                c = dbHandler.findTask(projection, selection, selectionArgs, sortOrder);
                break;
            case "Completed": // COMPLETED
                projection = new String[] {
                        TaskContract.TaskEntry._ID,
                        TaskContract.TaskEntry.COLUMN_NAME_COL1
                };
                selection = TaskContract.TaskEntry.COLUMN_NAME_COL4 + " = ?";
                selectionArgs = new String[] {"1"};
                sortOrder = null;
                c = dbHandler.findTask(projection, selection, selectionArgs, sortOrder);
                break;
            default:
        }
        /*Cursor c = dbHandler.findTask(projection, );
        if(c != null) {
            idView.setText("Found " + c.getCount() + " entries matching the query.");
        } else {
            idView.setText("No Match(es) Found");
        }*/
        // update the UI with the returned array
        updateUI(c);
    }

    public void deleteTaskClicked (View view) {
        /*boolean result = dbHandler.deleteTask(taskBox.getText().toString());
        if (result) {
            idView.setText("Record Deleted");
            taskBox.setText("");
        } else {
            idView.setText("No Match Found");
        }
        updateUI();*/
    }

    public void deleteSingleTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_col1);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE,
                TaskContract.TaskEntry.COLUMN_NAME_COL1 + " = ?",
                new String[]{task});
        db.close();
        updateUI();
    }

    private void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COLUMN_NAME_COL1},
                null, null, null, null, null);
        while(cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_NAME_COL1);
            taskList.add(cursor.getString(idx));
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.item_todo,
                    R.id.task_col1,
                    taskList);
            mTaskListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }

    private void updateUI(Cursor c) {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        while(c.moveToNext()) {
            int idx = c.getColumnIndex(TaskContract.TaskEntry.COLUMN_NAME_COL1);
            taskList.add(c.getString(idx));
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.item_todo,
                    R.id.task_col1,
                    taskList);
            mTaskListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }

        c.close();
        db.close();
    }
}
