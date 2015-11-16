package org.coursera.capstone.gotit.client.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.coursera.capstone.gotit.client.CallableTask;
import org.coursera.capstone.gotit.client.R;
import org.coursera.capstone.gotit.client.TaskCallback;
import org.coursera.capstone.gotit.client.model.Person;
import org.coursera.capstone.gotit.client.provider.ServiceApi;
import org.coursera.capstone.gotit.client.provider.ServiceCall;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by dtrotckii on 10/26/2015.
 */
public class RegistrationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    protected EditText birthDateEdit;

    private EditText reg_medical_record;

    private EditText reg_fullName;

    private EditText reg_login;

    private EditText reg_password;

    protected Switch hasDiabetesSwitch;

    private Date date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        reg_fullName = (EditText) findViewById(R.id.reg_fullname);

        birthDateEdit = (EditText) findViewById(R.id.birthDate);

        reg_login =  (EditText) findViewById(R.id.reg_login);

        reg_password =  (EditText) findViewById(R.id.reg_password);

        hasDiabetesSwitch = (Switch) findViewById(R.id.reg_has_diabetes);

        reg_medical_record = (EditText)findViewById(R.id.reg_medical_record);
        final TextView reg_medical_record_title = (TextView)findViewById(R.id.reg_medical_record_title);

        hasDiabetesSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visibility = hasDiabetesSwitch.isChecked() ? View.VISIBLE : View.GONE;
                reg_medical_record.setVisibility(visibility);
                reg_medical_record_title.setVisibility(visibility);
            }
        });
    }

    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setDateListener(this);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void onRegisterClick(View v) {
        v.setEnabled(false);

        String login = reg_login.getText().toString();
        String fullName = reg_fullName.getText().toString();
        String password = reg_password.getText().toString();
        boolean hasDiabetes = hasDiabetesSwitch.isChecked();
        String medicalRecordNumber = reg_medical_record.getText().toString();

        Person person = new Person(fullName, login, password, date.getTime(), hasDiabetes, medicalRecordNumber, null);
        registerUser(person);
    }

    private void registerUser(final Person person) {
        CallableTask.invoke(this, new ServiceCall<Void>() {
            @Override
            public Void call(ServiceApi srv) throws Exception {
                srv.registerUser(person);
                return null;
            }
        }, new TaskCallback<Void>() {
            @Override
            public void success(Void result) {
                Toast.makeText(RegistrationActivity.this, "User registered", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(RegistrationActivity.this, LoginScreenActivity.class);
                startActivity(i);
            }

            @Override
            public void error(Exception e) {
                Toast.makeText(RegistrationActivity.this, "User registered", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        birthDateEdit.setText(month+"/"+day+"/"+year);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        date = calendar.getTime();
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
