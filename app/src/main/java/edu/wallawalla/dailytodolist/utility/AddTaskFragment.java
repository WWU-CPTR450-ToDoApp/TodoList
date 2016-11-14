package edu.wallawalla.dailytodolist.utility;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import edu.wallawalla.dailytodolist.R;

public class AddTaskFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.add_task, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        mYear = year;
        mMonth = month;
        mDay = day;
        mText.setText("yoyo");
    }

    public static class DatePickerFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), (AddTaskFragment)getActivity(), year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
        }
    }
}
