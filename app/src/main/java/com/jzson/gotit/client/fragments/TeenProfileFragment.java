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

/**
 * A placeholder fragment containing a simple view.
 */
public class TeenProfileFragment extends Fragment {

    private Person mPerson;

    public TeenProfileFragment() {
        mPerson = AppApplication.getContext().getPerson();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_teen_profile, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentTeenProfileBinding bind = (FragmentTeenProfileBinding) DataBindingUtil.bind(view);
        bind.setPerson(mPerson);
    }
}