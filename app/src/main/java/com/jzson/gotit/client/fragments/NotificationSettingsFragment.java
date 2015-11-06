package com.jzson.gotit.client.fragments;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.jzson.gotit.client.AppApplication;
import com.jzson.gotit.client.R;
import com.jzson.gotit.client.model.GeneralSettings;
import com.jzson.gotit.client.provider.DataProvider;

import java.util.Calendar;
import java.util.Date;

public class NotificationSettingsFragment extends Fragment {

    private static final String NONE_TEXT = "None";
    private View.OnClickListener timeChangeListener = new View.OnClickListener() {

        @Override
        public void onClick(final View v) {
            showTimePickerDialog((TextView) v);
        }
    };

    private void showTimePickerDialog(final TextView textView) {
        Calendar mCurrentTime = Calendar.getInstance();
        int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mCurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(NotificationSettingsFragment.this.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String value = selectedHour + ":" + selectedMinute;

                textView.setText(value);

                Integer index = getTextViewIndexFromId(textView.getId());

                String key = alertKey[index];

                DataProvider.getInstance().saveGeneralSettings(userId, key, value);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Integer index = getTextViewIndexFromId(textView.getId());
                getSwitchByIndex(index).setChecked(false);
            }
        });
        mTimePicker.show();
    }

    private Integer getTextViewIndexFromId(int id) {
        for (int i=0; i<textViews.length; i++) {
            if (textViews[i] == id) {
                return i;
            }
        }
        return null;
    }


    private Integer getSwitchIndexFromId(int id) {
        for (int i=0; i<switches.length; i++) {
            if (switches[i] == id) {
                return i;
            }
        }
        return null;
    }

    private CompoundButton.OnCheckedChangeListener switchStateListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Integer index = getSwitchIndexFromId(buttonView.getId());
            getTextViewByIndex(index).setEnabled(isChecked);

            if (isChecked) {
                showTimePickerDialog(getTextViewByIndex(index));
            }
        }
    };

    private int[] textViews = {R.id.notification1_time, R.id.notification2_time, R.id.notification3_time};

    private int[] switches = {R.id.notification1_switch, R.id.notification2_switch, R.id.notification3_switch};

    private String[] alertKey = {GeneralSettings.ALERT_1, GeneralSettings.ALERT_2, GeneralSettings.ALERT_3};

    private int userId = AppApplication.getContext().getUserId();

    private View rootView;

    public NotificationSettingsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_settings_notification , container, false);

        final Switch generalSwitch = (Switch) rootView.findViewById(R.id.notification_switch_general);

        generalSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    for (int i = 0; i < switches.length; i++) {
                        if (!isChecked) {
                            getSwitchByIndex(i).setChecked(false);
                            getSwitchByIndex(i).setEnabled(false);
                            getTextViewByIndex(i).setEnabled(false);
                            getTextViewByIndex(i).setText(NONE_TEXT);
                        } else {
                            getSwitchByIndex(i).setEnabled(true);
                        }
                    }
            }
        });

        boolean notificationEnabled = false;
        for (int i=0; i<alertKey.length; i++) {
            String time = DataProvider.getInstance().loadGeneralSettings(userId, alertKey[i]);
            if (time != null) {
                getTextViewByIndex(i).setText(time);
                getTextViewByIndex(i).setEnabled(true);
                getSwitchByIndex(i).setChecked(true);
                notificationEnabled = true;
            } else {
                getTextViewByIndex(i).setText(NONE_TEXT);
                getTextViewByIndex(i).setEnabled(false);
                getSwitchByIndex(i).setChecked(false);
            }
        }

        generalSwitch.setChecked(notificationEnabled);

        for (int i=0; i<textViews.length; i++) {
            TextView timeText = getTextViewByIndex(i);
            timeText.setOnClickListener(timeChangeListener);
        }

        for (int i=0; i<switches.length; i++) {
            Switch switch1 = getSwitchByIndex(i);
            switch1.setOnCheckedChangeListener(switchStateListener);
        }

        return rootView;
    }

    private TextView getTextViewByIndex(int index) {
        return (TextView) rootView.findViewById(textViews[index]);
    }

    private Switch getSwitchByIndex(int index) {
        return (Switch) rootView.findViewById(switches[index]);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}