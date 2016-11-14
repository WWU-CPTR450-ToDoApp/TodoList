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



    public void addTaskClicked(View view) {

        DialogFragment addfrag = AddTaskFragment.newInstance();
        addfrag.show(getSupportFragmentManager(), "addTask");



        /*LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View addTaskView = layoutInflater.inflate(R.layout.add_task, null);
        // create a builder for a new alert dialog
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        // set add_task.xml to be layout file for the alertdialog builder
        adb.setView(addTaskView);
        final EditText date = (EditText) addTaskView.findViewById(R.id.date);
        // Show a datepicker when the date field is clicked
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTaskFragment dpa = new AddTaskFragment(date);
            }
        });
        adb.show();*/
        //ToDoTask task = new ToDoTask(taskBox.getText().toString());
        //dbHandler.addTask(task);
        //taskBox.setText("");
        //updateUI();
    }
    public void findTaskClicked (View view) {
        /*Cursor c = dbHandler.findTask(taskBox.getText().toString(), taskBox.getText().toString());
        if(c != null) {
            idView.setText("Found " + c.getCount() + " entries matching the query.");
        } else {
            idView.setText("No Match(es) Found");
        }
        // update the UI with the returned array
        updateUI(c);*/
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




    public static class AddTaskFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        EditText date;

        public static AddTaskFragment newInstance() {
            AddTaskFragment frag = new AddTaskFragment();
            Bundle args = new Bundle();
            frag.setArguments(args);
            return frag;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final View view = inflater.inflate(R.layout.add_task, container, false);
            final Activity activity = getActivity();
            return view;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            final View addTaskView = inflater.inflate(R.layout.add_task, null, false);

            date = (EditText) addTaskView.findViewById(R.id.date);
            // Show a date-picker when the date field is clicked
            date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar c = Calendar.getInstance();
                    DatePickerDialog dfrag;
                    dfrag = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int day) {
                            date.setText("HI");
                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
                    dfrag.show();
                }
            });

            // Create a new instance of DatePickerDialog and return it
            return new AlertDialog.Builder(getActivity())
                    .setTitle("New Task")
                    .setMessage("Add a new task")
                    .setView(addTaskView)
                    .create();
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
        }

        public static class DatePickerFragment extends DialogFragment
                implements DatePickerDialog.OnDateSetListener {

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                // Use the current date as the default date in the picker
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // Create a new instance of DatePickerDialog and return it
                return new DatePickerDialog(getActivity(), this, year, month, day);
            }

            public void onDateSet(DatePicker view, int year, int month, int day) {
                // Do something with the date chosen by the user
            }
        }
    }
}
