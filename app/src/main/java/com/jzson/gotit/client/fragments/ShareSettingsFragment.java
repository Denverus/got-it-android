package com.jzson.gotit.client.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.jzson.gotit.client.AppApplication;
import com.jzson.gotit.client.CallableTask;
import com.jzson.gotit.client.R;
import com.jzson.gotit.client.TaskCallback;
import com.jzson.gotit.client.adapter.SettingsFollowerListAdapter;
import com.jzson.gotit.client.adapter.base.BaseListAdapter;
import com.jzson.gotit.client.provider.ServiceApi;
import com.jzson.gotit.client.provider.ServiceCall;

/**
 * A placeholder fragment containing a simple view.
 */
public class ShareSettingsFragment extends Fragment implements BaseListAdapter.DataUpdateListener {

    private interface SharingResultHandler {
        void onSettingsReceived(Boolean isSharingEnabled);
    }

    private RecyclerView mRecyclerView;
    private BaseListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private final int userId = AppApplication.getContext().getUserId();

    public ShareSettingsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_share_settings, container, false);

        final Switch settingsSwitch = (Switch)rootView.findViewById(R.id.switch_share_data);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv);

        mRecyclerView.setHasFixedSize(false);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(0);

        mAdapter = new SettingsFollowerListAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setDataUpdateListener(this);
        mAdapter.refreshData();

        mRecyclerView.animate().setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);


            }
        });

        isSharingEnabled(userId, new SharingResultHandler() {
            @Override
            public void onSettingsReceived(Boolean isSharingEnabled) {
                settingsSwitch.setChecked(isSharingEnabled);
                mRecyclerView.setVisibility(isSharingEnabled ? View.VISIBLE : View.GONE);
            }
        });

        mRecyclerView.animate().setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!settingsSwitch.isChecked()) {
                    mRecyclerView.setVisibility(View.GONE);
                }
            }
        });

        settingsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showFollowerList(isChecked);

                setSharingEnabled(userId, isChecked);
            }
        });

        return rootView;
    }

    private void isSharingEnabled(final int userId, final SharingResultHandler sharingResultHandler) {
        CallableTask.invoke(getContext(), new ServiceCall<Boolean>() {
            @Override
            public Boolean call(ServiceApi srv) throws Exception {
                return srv.isSharingEnabled(userId);
            }
        }, new TaskCallback<Boolean>() {
            @Override
            public void success(Boolean result) {
                sharingResultHandler.onSettingsReceived(result);
            }

            @Override
            public void error(Exception e) {
                Toast.makeText(getContext(), "Can't load settings", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSharingEnabled(final int userId, final boolean isChecked) {
        CallableTask.invoke(getContext(), new ServiceCall<Void>() {
            @Override
            public Void call(ServiceApi srv) throws Exception {
                srv.setSharingEnabled(userId, isChecked);
                return null;
            }
        }, new TaskCallback<Void>() {
            @Override
            public void success(Void result) {
                Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void error(Exception e) {
                Toast.makeText(getContext(), "Can't save settings", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showFollowerList(boolean isChecked) {
        if (isChecked) {
            mRecyclerView.setTranslationY(-mRecyclerView.getHeight());
            mRecyclerView.setVisibility(View.VISIBLE);
            mRecyclerView.animate().translationY(0).start();
        } else {
            mRecyclerView.setTranslationY(0);
            mRecyclerView.animate()
                    .translationY(-mRecyclerView.getHeight()).start();
        }
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isSharingEnabled(userId, new SharingResultHandler() {
            @Override
            public void onSettingsReceived(Boolean isSharingEnabled) {
                showFollowerList(isSharingEnabled);
            }
        });
    }

    @Override
    public void onDataUpdated() {

    }
}