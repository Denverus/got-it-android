package org.coursera.capstone.gotit.client.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.coursera.capstone.gotit.client.AppApplication;
import org.coursera.capstone.gotit.client.CallableTask;
import org.coursera.capstone.gotit.client.NavUtils;
import org.coursera.capstone.gotit.client.TaskCallback;
import org.coursera.capstone.gotit.client.model.Answer;
import org.coursera.capstone.gotit.client.model.Question;
import org.coursera.capstone.gotit.client.provider.ServiceApi;
import org.coursera.capstone.gotit.client.provider.ServiceCall;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        View rootView = inflater.inflate(org.coursera.capstone.gotit.client.R.layout.fragment_check_in, container, false);

        RelativeLayout answerLayout = (RelativeLayout) rootView.findViewById(org.coursera.capstone.gotit.client.R.id.answer_frame);

        questionTextView = (TextView) rootView.findViewById(org.coursera.capstone.gotit.client.R.id.question);

        answerEditText = (EditText) rootView.findViewById(org.coursera.capstone.gotit.client.R.id.edtText);
        answerEditNumber = (EditText) rootView.findViewById(org.coursera.capstone.gotit.client.R.id.editInt);
        answerEditBool = (Switch) rootView.findViewById(org.coursera.capstone.gotit.client.R.id.editBool);

        progressBar = (ProgressBar) rootView.findViewById(org.coursera.capstone.gotit.client.R.id.progressBar);
    /*
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.content_frame, new TeenListFragment())
                .commit();
*/

        questionList = AppApplication.getContext().getQuestionList();

        skipButton = (Button) rootView.findViewById(org.coursera.capstone.gotit.client.R.id.skipButton);
        nextButton = (Button) rootView.findViewById(org.coursera.capstone.gotit.client.R.id.nextButton);

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
                int userId = AppApplication.getContext().getCurrentUserId();
                saveAnswers(userId, answerList);
            } else {
                Toast.makeText(getContext(), "Check In doesn't complete", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveAnswers(final int userId, final List<Answer> answerList) {
        CallableTask.invoke(getContext(), new ServiceCall<Void>() {
            @Override
            public Void call(ServiceApi srv) throws Exception {
                srv.saveAnswer(new Date().getTime(), userId, answerList);
                return null;
            }
        }, new TaskCallback<Void>() {
            @Override
            public void success(Void result) {
                Toast.makeText(getContext(), "Check In saved", Toast.LENGTH_SHORT).show();
                NavUtils.showCheckInList(getContext());
            }

            @Override
            public void error(Exception e) {
                Toast.makeText(getContext(), "Error during saving", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Answer readAnswer() {
        Question question = questionList.get(questionIndex - 1);
        int questionId = question.getId();
        int inputType = question.getAnswerType();

        Answer answer = null;

        switch (inputType) {
            case Answer.TYPE_STRING: {
                if (!answerEditText.getText().toString().isEmpty()) {
                    String answerText = answerEditText.getText().toString();
                    answer = new Answer(questionId, answerText);
                }
                break;
            }
            case Answer.TYPE_INT: {
                if (!answerEditNumber.getText().toString().isEmpty()) {
                    Integer answerInt = Integer.parseInt(answerEditNumber.getText().toString());
                    answer = new Answer(questionId, answerInt);
                }
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
                showKeyboard(answerEditText);
                break;
            }
            case Answer.TYPE_INT: {
                answerEditText.setVisibility(View.GONE);
                answerEditNumber.setVisibility(View.VISIBLE);
                answerEditBool.setVisibility(View.GONE);
                showKeyboard(answerEditNumber);
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

    private void showKeyboard(View v) {
        InputMethodManager inputMethodManager =  (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
        v.requestFocus();
    }


    private void hideKeyboard(View v) {
        InputMethodManager inputMethodManager =  (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}
