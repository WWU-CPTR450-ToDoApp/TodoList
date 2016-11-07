package edu.wallawalla.dailytodolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import edu.wallawalla.dailytodolist.db.DBHandler;
import edu.wallawalla.dailytodolist.db.ToDoTask;

public class MainActivity extends AppCompatActivity {
    TextView idView;
    EditText taskBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idView = (TextView) findViewById(R.id.taskID);
        taskBox = (EditText) findViewById(R.id.taskName);
        // comment
    }

    public void newTask (View view) {
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        ToDoTask task = new ToDoTask(taskBox.getText().toString());
        dbHandler.addTask(task);
        taskBox.setText("");
    }
    public void lookupTask (View view) {
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        ToDoTask task = dbHandler.findTask(taskBox.getText().toString());
        if(task != null) {
            idView.setText(String.valueOf(task.getID()));
        } else {
            idView.setText("No Match Found");
        }
    }

    public void removeTask (View view) {
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        boolean result = dbHandler.deleteTask(taskBox.getText().toString());
        if (result) {
            idView.setText("Record Deleted");
            taskBox.setText("");
        } else {
            idView.setText("No Match Found");
        }
    }
}
