package com.jzson.gotit.client.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jzson.gotit.client.AppApplication;
import com.jzson.gotit.client.R;
import com.jzson.gotit.client.model.Question;

import java.util.List;

/**
 * Created by Denis on 10/27/2015.
 */
public class NewCheckInFragment extends Fragment {

    private List<Question> questionList;

    private int questionIndex;

    private TextView questionTextView;

    private EditText answerEditText;

    private Button lastButton;

    private Button skipButton;

    private Button nextButton;

    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_check_in, container, false);

        RelativeLayout answerLayout = (RelativeLayout) rootView.findViewById(R.id.answer_frame);

        questionTextView = (TextView) rootView.findViewById(R.id.question);

        answerEditText = (EditText) rootView.findViewById(R.id.edtText);

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
    /*
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.content_frame, new TeenListFragment())
                .commit();
*/

        questionList = AppApplication.getContext().getQuestionList();

        lastButton = (Button) rootView.findViewById(R.id.lastButton);
        skipButton = (Button) rootView.findViewById(R.id.skipButton);
        nextButton = (Button) rootView.findViewById(R.id.nextButton);

        lastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (questionIndex > 0) {
                    Question question = questionList.get(--questionIndex);
                    questionTextView.setText(question.getQuestion());
                }
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Question question = questionList.get(questionIndex++);
                questionTextView.setText(question.getQuestion());
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (questionIndex < questionList.size()) {
                    Question question = questionList.get(questionIndex++);
                    questionTextView.setText(question.getQuestion());
                    progressBar.setProgress(questionIndex * 100 / questionList.size());
                } else {
                    nextButton.setEnabled(false);
                }
            }
        });
        return rootView;
    }
}
