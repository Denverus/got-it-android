package org.coursera.capstone.gotit.client.provider;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.coursera.capstone.gotit.client.model.Answer;
import org.coursera.capstone.gotit.client.model.CheckIn;

import java.lang.reflect.Type;

public class CheckinDeserializer implements JsonDeserializer<CheckIn>
{
    @Override
    public CheckIn deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
            throws JsonParseException
    {
        CheckIn checkIn = new Gson().fromJson(je.getAsJsonObject(), CheckIn.class);

        JsonElement content = je.getAsJsonObject().get("answers");

        JsonArray answerArray = content.getAsJsonArray();
        Answer[] answers = new Answer[answerArray.size()];
        for (int i=0; i<answerArray.size(); i++) {
            JsonElement answerJson = answerArray.get(i);
            Answer answer = new Gson().fromJson(answerJson, Answer.class);
            answers[i] = answer;
        }

        checkIn.setAnswers(answers);

        return checkIn;
    }
}