package com.jzson.gotit.client.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jzson.gotit.client.NavUtils;
import com.jzson.gotit.client.R;
import com.jzson.gotit.client.databinding.FragmentTeenProfileBinding;

/**
 * A placeholder fragment containing a simple view.
 */
public class SettingsFragment extends Fragment {

    FragmentTeenProfileBinding bind;

    public SettingsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        TextView sharingSettings = (TextView) rootView.findViewById(R.id.settings_share);
        TextView sharingStatusSettings = (TextView) rootView.findViewById(R.id.settings_share_status);
        TextView notificationSettings = (TextView) rootView.findViewById(R.id.settings_notification);
        TextView notificationStatusSettings = (TextView) rootView.findViewById(R.id.settings_notification_status);

        sharingSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.showShareSettings(getActivity());
            }
        });

        notificationSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.showNotificationSettings(getActivity());
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}