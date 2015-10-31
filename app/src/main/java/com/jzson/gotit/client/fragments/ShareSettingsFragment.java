package com.jzson.gotit.client.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzson.gotit.client.AppApplication;
import com.jzson.gotit.client.R;
import com.jzson.gotit.client.databinding.FragmentTeenProfileBinding;
import com.jzson.gotit.client.model.Person;
import com.jzson.gotit.client.provider.DataProvider;

/**
 * A placeholder fragment containing a simple view.
 */
public class ShareSettingsFragment extends Fragment implements View.OnClickListener {

    private Person mPerson;

    FragmentTeenProfileBinding bind;

    public ShareSettingsFragment() {
        mPerson = AppApplication.getContext().getPerson();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_share_settings, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bind = (FragmentTeenProfileBinding) DataBindingUtil.bind(view);
        bind.setPerson(mPerson);
        bind.subscribeButton.setOnClickListener(this);

        int followerStatus = DataProvider.getInstance().checkFollowerStatus(AppApplication.getContext().getUserId(), mPerson.getId());
        if (followerStatus == DataProvider.FOLLOWER_STATUS_FOLLOWED) {
            bind.subscribeButton.setText(getContext().getResources().getString(R.string.unfollow));
        } else if (followerStatus == DataProvider.FOLLOWER_STATUS_NOT_FOLLOWED) {
            bind.subscribeButton.setText(getContext().getResources().getString(R.string.follow));
        } else if (followerStatus == DataProvider.FOLLOWER_STATUS_REQUESTED) {
            bind.subscribeButton.setText(getContext().getResources().getString(R.string.requested));
            bind.subscribeButton.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        bind.subscribeButton.setText(R.string.requested);
        bind.subscribeButton.setEnabled(false);
        DataProvider.getInstance().sendSubscribeRequest(mPerson.getId(), AppApplication.getContext().getUserId());
    }
}