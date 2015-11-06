package com.jzson.gotit.client.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;

import com.jzson.gotit.client.AppApplication;
import com.jzson.gotit.client.R;
import com.jzson.gotit.client.adapter.SettingsFollowerListAdapter;
import com.jzson.gotit.client.adapter.base.BaseListAdapter;
import com.jzson.gotit.client.animation.AnimationFactory;
import com.jzson.gotit.client.databinding.FragmentShareSettingsBinding;
import com.jzson.gotit.client.databinding.FragmentTeenProfileBinding;
import com.jzson.gotit.client.model.FollowerSettings;
import com.jzson.gotit.client.model.Person;
import com.jzson.gotit.client.model.ShareSettings;
import com.jzson.gotit.client.provider.DataProvider;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ShareSettingsFragment extends Fragment implements BaseListAdapter.DataUpdateListener {

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

        boolean isSharingEnabled = DataProvider.getInstance().isSharingEnabled(userId);
        settingsSwitch.setChecked(isSharingEnabled);

        mRecyclerView.setVisibility(isSharingEnabled ? View.VISIBLE : View.GONE);

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

                DataProvider.getInstance().setSharingEnabled(userId, isChecked);
            }
        });

        return rootView;
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
        boolean isSharingEnabled = DataProvider.getInstance().isSharingEnabled(userId);
        showFollowerList(isSharingEnabled);
    }

    @Override
    public void onDataUpdated() {

    }
}