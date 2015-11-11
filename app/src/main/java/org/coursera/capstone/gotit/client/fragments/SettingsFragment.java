package org.coursera.capstone.gotit.client.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.coursera.capstone.gotit.client.AppApplication;
import org.coursera.capstone.gotit.client.CallableTask;
import org.coursera.capstone.gotit.client.NavUtils;
import org.coursera.capstone.gotit.client.TaskCallback;
import org.coursera.capstone.gotit.client.model.GeneralSettings;
import org.coursera.capstone.gotit.client.provider.ServiceApi;
import org.coursera.capstone.gotit.client.provider.ServiceCall;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class SettingsFragment extends Fragment {

    private TextView sharingStatusSettings;

    private TextView alertStatusSettings;

    private String[] alertKey = {GeneralSettings.ALERT_1, GeneralSettings.ALERT_2, GeneralSettings.ALERT_3};

    public SettingsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(org.coursera.capstone.gotit.client.R.layout.fragment_settings, container, false);

        TextView sharingSettings = (TextView) rootView.findViewById(org.coursera.capstone.gotit.client.R.id.settings_share);
        sharingStatusSettings = (TextView) rootView.findViewById(org.coursera.capstone.gotit.client.R.id.settings_share_status);
        TextView alertSettings = (TextView) rootView.findViewById(org.coursera.capstone.gotit.client.R.id.settings_notification);
        alertStatusSettings = (TextView) rootView.findViewById(org.coursera.capstone.gotit.client.R.id.settings_notification_status);

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

        int userId = AppApplication.getContext().getCurrentUserId();

        isSharingEnabled(userId);

        loadGeneralSettingsList(userId, alertKey);

        return rootView;
    }

    private void loadGeneralSettingsList(final int userId, final String[] alertKey) {
        CallableTask.invoke(getContext(), new ServiceCall<List<GeneralSettings>>() {
            @Override
            public List<GeneralSettings> call(ServiceApi srv) throws Exception {
                return srv.loadGeneralSettingsList(userId, alertKey);
            }
        }, new TaskCallback<List<GeneralSettings>>() {
            @Override
            public void success(List<GeneralSettings> settingsList) {
                StringBuilder sb = new StringBuilder();
                for (GeneralSettings settings : settingsList) {
                    if (settings.getValue() != null) {
                        sb.append(settings.getValue());
                        sb.append(" ");
                    }
                }
                alertStatusSettings.setText(sb.toString().isEmpty() ? "No alerts" : sb.toString());
            }

            @Override
            public void error(Exception e) {

            }
        });
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void isSharingEnabled(final int userId) {
        CallableTask.invoke(getContext(), new ServiceCall<Boolean>() {
            @Override
            public Boolean call(ServiceApi srv) throws Exception {
                return srv.isSharingEnabled(userId);
            }
        }, new TaskCallback<Boolean>() {
            @Override
            public void success(Boolean sharingEnabled) {
                sharingStatusSettings.setText(sharingEnabled ? "Enabled" : "Disabled");
            }

            @Override
            public void error(Exception e) {
                Toast.makeText(getContext(), "Can't load settings", Toast.LENGTH_SHORT).show();
            }
        });
    }
}