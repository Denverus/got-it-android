package com.jzson.gotit.client.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jzson.gotit.client.AppApplication;
import com.jzson.gotit.client.NavUtils;
import com.jzson.gotit.client.R;
import com.jzson.gotit.client.activities.MainActivity;
import com.jzson.gotit.client.databinding.FragmentEditFeedbackBinding;
import com.jzson.gotit.client.model.Feedback;
import com.jzson.gotit.client.model.Person;
import com.jzson.gotit.client.provider.DataProvider;

/**
 * A placeholder fragment containing a simple view.
 */
public class EditFeedbackFragment extends Fragment implements View.OnClickListener {

    private Person mPerson;

    private FragmentEditFeedbackBinding bind;

    private Button mSaveButton;

    public EditFeedbackFragment() {
        mPerson = AppApplication.getContext().getPerson();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_feedback, container, false);

        mSaveButton = (Button) rootView.findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bind = (FragmentEditFeedbackBinding) DataBindingUtil.bind(view);
        bind.setFeedback(AppApplication.getContext().getFeedback());
    }

    @Override
    public void onClick(View v) {
        mSaveButton.setEnabled(false);
        Feedback feedback = bind.getFeedback();
        DataProvider.getInstance().addFeedback(AppApplication.getContext().getUserId(), feedback);
        NavUtils.showEditFeedback(getContext());
    }
}