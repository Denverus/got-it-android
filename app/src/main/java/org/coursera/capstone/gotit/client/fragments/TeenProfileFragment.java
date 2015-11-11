package org.coursera.capstone.gotit.client.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.coursera.capstone.gotit.client.AppApplication;
import org.coursera.capstone.gotit.client.CallableTask;
import org.coursera.capstone.gotit.client.TaskCallback;
import org.coursera.capstone.gotit.client.databinding.FragmentTeenProfileBinding;
import org.coursera.capstone.gotit.client.model.Person;
import org.coursera.capstone.gotit.client.provider.InternalProvider;
import org.coursera.capstone.gotit.client.provider.ServiceApi;
import org.coursera.capstone.gotit.client.provider.ServiceCall;

/**
 * A placeholder fragment containing a simple view.
 */
public class TeenProfileFragment extends Fragment implements View.OnClickListener {

    private Person mPerson;

    FragmentTeenProfileBinding bind;

    public TeenProfileFragment() {
        mPerson = AppApplication.getContext().getPerson();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(org.coursera.capstone.gotit.client.R.layout.fragment_teen_profile, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bind = (FragmentTeenProfileBinding) DataBindingUtil.bind(view);
        bind.setPerson(mPerson);
        bind.subscribeButton.setOnClickListener(this);

        checkFollowerStatus(AppApplication.getContext().getCurrentUserId(), mPerson.getId());
    }

    private void checkFollowerStatus(final int userId, final int followerId) {
        CallableTask.invoke(getContext(), new ServiceCall<Integer>() {
            @Override
            public Integer call(ServiceApi srv) throws Exception {
                return srv.checkFollowerStatus(userId, followerId);
            }
        }, new TaskCallback<Integer>() {
            @Override
            public void success(Integer followerStatus) {
                if (followerStatus == InternalProvider.FOLLOWER_STATUS_FOLLOWED) {
                    bind.subscribeButton.setText(getContext().getResources().getString(org.coursera.capstone.gotit.client.R.string.unfollow));
                } else if (followerStatus == InternalProvider.FOLLOWER_STATUS_NOT_FOLLOWED) {
                    bind.subscribeButton.setText(getContext().getResources().getString(org.coursera.capstone.gotit.client.R.string.follow));
                } else if (followerStatus == InternalProvider.FOLLOWER_STATUS_REQUESTED) {
                    bind.subscribeButton.setText(getContext().getResources().getString(org.coursera.capstone.gotit.client.R.string.requested));
                    bind.subscribeButton.setEnabled(false);
                }
            }

            @Override
            public void error(Exception e) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        bind.subscribeButton.setText(org.coursera.capstone.gotit.client.R.string.requested);
        bind.subscribeButton.setEnabled(false);
        sendSubscribeRequest(mPerson.getId(), AppApplication.getContext().getCurrentUserId());
    }

    private void sendSubscribeRequest(int followerId, int userId) {
        CallableTask.invoke(getContext(), new ServiceCall<Void>() {
            @Override
            public Void call(ServiceApi srv) throws Exception {
                srv.sendSubscribeRequest(mPerson.getId(), AppApplication.getContext().getCurrentUserId());;
                return null;
            }
        }, new TaskCallback<Void>() {
            @Override
            public void success(Void result) {

            }

            @Override
            public void error(Exception e) {

            }
        });
    }
}