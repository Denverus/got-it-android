package com.jzson.gotit.client.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jzson.gotit.client.AppApplication;
import com.jzson.gotit.client.NavUtils;
import com.jzson.gotit.client.R;
import com.jzson.gotit.client.model.GeneralSettings;
import com.jzson.gotit.client.provider.InternalProvider;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class SettingsFragment extends Fragment {

    private String[] alertKey = {GeneralSettings.ALERT_1, GeneralSettings.ALERT_2, GeneralSettings.ALERT_3};

    public SettingsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        TextView sharingSettings = (TextView) rootView.findViewById(R.id.settings_share);
        TextView sharingStatusSettings = (TextView) rootView.findViewById(R.id.settings_share_status);
        TextView alertSettings = (TextView) rootView.findViewById(R.id.settings_notification);
        TextView alertStatusSettings = (TextView) rootView.findViewById(R.id.settings_notification_status);

        sharingSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.showShareSettings(getActivity());
            }
        });

        alertSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.showNotificationSettings(getActivity());
            }
        });

        int userId = AppApplication.getContext().getUserId();

        boolean sharingEnabled = InternalProvider.getInstance().isSharingEnabled(userId);
        sharingStatusSettings.setText(sharingEnabled ? "Enabled" : "Disabled");

        List<GeneralSettings> settingsList = InternalProvider.getInstance().loadGeneralSettingsList(userId, alertKey);
        StringBuilder sb = new StringBuilder();
        for (GeneralSettings settings : settingsList) {
            if (settings.getValue() != null) {
                sb.append(settings.getValue());
                sb.append(" ");
            }
        }
        alertStatusSettings.setText(sb.toString().isEmpty() ? "No alerts" : sb.toString());

        return rootView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}