package com.jzson.gotit.client.provider;

import com.jzson.gotit.client.model.Feedback;
import com.jzson.gotit.client.model.Person;
import com.jzson.gotit.client.model.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis on 10/11/2015.
 */
public class DataProvider {
    private static final DataProvider INSTANCE = new DataProvider();

    private List<Person> persons;

    private List<Feedback> feedbacks;

    private List<Question> question;

    public DataProvider() {
        initializeData();
    }

    private void initializeData(){
        persons = new ArrayList<>();
        persons.add(new Person("Emma Wilson", "23 years old"));
        persons.add(new Person("Lavery Maiss", "25 years old"));
        persons.add(new Person("Lillie Watts", "35 years old"));

        feedbacks = new ArrayList<>();
        feedbacks.add(new Feedback(createQuestions(10d, "Meat", false)));
        feedbacks.add(new Feedback(createQuestions(5d, "Bread", true)));
        feedbacks.add(new Feedback(createQuestions(3d, "Soup", false)));
        feedbacks.add(new Feedback(createQuestions(12d, "Sandwich", true)));
    }

    public static DataProvider getInstance() {
        return INSTANCE;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    private List<Question> createQuestions(Double sugarLevel, String meal, Boolean adminInsulin) {
        question = new ArrayList<>();
        question.add(new Question("What was your blood sugar level at meal time?", sugarLevel, Question.QUESTION_SUGAR_LEVEL));
        question.add(new Question("What did you eat at meal time?", meal, Question.QUESTION_MEAL));
        question.add(new Question("Did you administer insulin?", adminInsulin, Question.QUESTION_INSULIN));
        return question;
    }
}
