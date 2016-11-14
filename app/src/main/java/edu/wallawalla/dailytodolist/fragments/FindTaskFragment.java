package edu.wallawalla.dailytodolist.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import edu.wallawalla.dailytodolist.MainActivity;
import edu.wallawalla.dailytodolist.R;

public class FindTaskFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // create a view based on add_task.xml
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View findTaskView = inflater.inflate(R.layout.find_task, null, false);

        // Create a new instance of DatePickerDialog and return it
        return new AlertDialog.Builder(getActivity())
                .setTitle("Sort Tasks")
                .setMessage("Select sort by:")
                .setView(findTaskView)
                // Create the Sort button to sort the tasks in the database
                .setPositiveButton("Sort", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RadioGroup rg = (RadioGroup) findTaskView.findViewById(R.id.rb_sortgroup);
                        RadioButton rb = (RadioButton) findTaskView.findViewById(rg.getCheckedRadioButtonId());
                        ((MainActivity) getActivity()).findTaskMethod(
                                rb.getText().toString()
                        );
                    }
                })
                // Create a Cancel button to cancel creating a new task
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create();
    }
}