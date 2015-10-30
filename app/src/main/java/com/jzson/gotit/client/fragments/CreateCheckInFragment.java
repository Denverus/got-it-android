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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.jzson.gotit.client.AppApplication;
import com.jzson.gotit.client.NavUtils;
import com.jzson.gotit.client.R;
import com.jzson.gotit.client.model.Answer;
import com.jzson.gotit.client.model.Question;
import com.jzson.gotit.client.provider.DataProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis on 10/27/2015.
 */
public class CreateCheckInFragment extends Fragment {

    private int questionIndex;
    private TextView questionTextView;
    private EditText answerEditText;
    private EditText answerEditNumber;
    private Switch answerEditBool;
    private Button skipButton;
    private Button nextButton;
    private ProgressBar progressBar;

    private List<Question> questionList;
    private List<Answer> answerList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_check_in, container, false);

        RelativeLayout answerLayout = (RelativeLayout) rootView.findViewById(R.id.answer_frame);

        questionTextView = (TextView) rootView.findViewById(R.id.question);

        answerEditText = (EditText) rootView.findViewById(R.id.edtText);
        answerEditNumber = (EditText) rootView.findViewById(R.id.editInt);
        answerEditBool = (Switch) rootView.findViewById(R.id.editBool);

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
    /*
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.content_frame, new TeenListFragment())
                .commit();
*/

        questionList = AppApplication.getContext().getQuestionList();

        skipButton = (Button) rootView.findViewById(R.id.skipButton);
        nextButton = (Button) rootView.findViewById(R.id.nextButton);

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToNextStep(false);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToNextStep(true);
            }
        });

        openNextQuestion();

        return rootView;
    }

    private void openNextQuestion() {
        Question question = questionList.get(questionIndex++);
        questionTextView.setText(question.getQuestion());
        switchInputControl(question.getAnswerType());
    }

    private void moveToNextStep(boolean save) {
        if (save) {
            Answer answer = readAnswer();
            if (answer != null) {
                answerList.add(answer);
            }
        }

        if (questionIndex < questionList.size()) {
            progressBar.setProgress(questionIndex * 100 / questionList.size());
            openNextQuestion();
        } else {
            if (!answerList.isEmpty()) {
                int userId = AppApplication.getContext().getUserId();
                DataProvider.getInstance().saveAnswer(userId, answerList);
                NavUtils.showCheckInList(getContext());
                Toast.makeText(getContext(), "Check In saved", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Check In doesn't complete", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Answer readAnswer() {
        Question question = questionList.get(questionIndex - 1);
        int questionId = question.getId();
        int inputType = question.getAnswerType();

        Answer answer = null;

        switch (inputType) {
            case Answer.TYPE_STRING: {
                String answerText = answerEditText.getText().toString();
                answer = new Answer(questionId, answerText);
                break;
            }
            case Answer.TYPE_INT: {
                Integer answerInt = Integer.parseInt(answerEditNumber.getText().toString());
                answer = new Answer(questionId, answerInt);
                break;
            }
            case Answer.TYPE_BOOLEAN: {
                Boolean answerBool = answerEditBool.isChecked();
                answer = new Answer(questionId, answerBool);
                break;
            }
        }

        return answer;
    }

    private void switchInputControl(int inputType) {
        switch (inputType) {
            case Answer.TYPE_STRING: {
                answerEditText.setVisibility(View.VISIBLE);
                answerEditNumber.setVisibility(View.GONE);
                answerEditBool.setVisibility(View.GONE);
                break;
            }
            case Answer.TYPE_INT: {
                answerEditText.setVisibility(View.GONE);
                answerEditNumber.setVisibility(View.VISIBLE);
                answerEditBool.setVisibility(View.GONE);
                break;
            }
            case Answer.TYPE_BOOLEAN: {
                answerEditText.setVisibility(View.GONE);
                answerEditNumber.setVisibility(View.GONE);
                answerEditBool.setVisibility(View.VISIBLE);
                break;
            }
        }
    }
}
