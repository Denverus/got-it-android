package com.jzson.gotit.client.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.jzson.gotit.client.R;

import java.util.Calendar;

/**
 * Created by dtrotckii on 10/26/2015.
 */
public class RegistrationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    protected EditText birthDateEdit;

    protected Switch hasDiabetesSwitch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        birthDateEdit = (EditText) findViewById(R.id.birthDate);

        hasDiabetesSwitch = (Switch) findViewById(R.id.reg_has_diabetes);

        final EditText reg_medical_record = (EditText)findViewById(R.id.reg_medical_record);
        final TextView reg_medical_record_title = (TextView)findViewById(R.id.reg_medical_record_title);

        hasDiabetesSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visibility = hasDiabetesSwitch.isChecked() ? View.VISIBLE : View.GONE;
                reg_medical_record.setVisibility(visibility);
                reg_medical_record_title.setVisibility(visibility);
            }
        });

        //TextView registerScreen = (TextView) findViewById(R.id.link_to_register);

        // Listening to register new account link
        /*registerScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });*/
    }

    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setDateListener(this);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        birthDateEdit.setText(month+"/"+day+"/"+year);
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private DatePickerDialog.OnDateSetListener dateListener;

        public void setDateListener(DatePickerDialog.OnDateSetListener dateListener) {
            this.dateListener = dateListener;
        }

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
            dateListener.onDateSet(view, year, month, day);
        }
    }
}
