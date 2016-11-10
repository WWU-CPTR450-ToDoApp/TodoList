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

    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idView = (TextView) findViewById(R.id.taskID);
        taskBox = (EditText) findViewById(R.id.taskName);
        dbHandler = new DBHandler(this);
    }

    public void newTask (View view) {
        ToDoTask task = new ToDoTask(taskBox.getText().toString());
        dbHandler.addTask(task);
        taskBox.setText("");
    }
    public void lookupTask (View view) {
        ToDoTask task = dbHandler.findTask(taskBox.getText().toString());
        if(task != null) {
            idView.setText(String.valueOf(task.getID()));
        } else {
            idView.setText("No Match Found");
        }
    }

    public void removeTask (View view) {
        boolean result = dbHandler.deleteTask(taskBox.getText().toString());
        if (result) {
            idView.setText("Record Deleted");
            taskBox.setText("");
        } else {
            idView.setText("No Match Found");
        }
    }
}
