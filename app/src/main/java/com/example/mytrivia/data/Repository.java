package com.example.mytrivia.data;

import android.util.Log;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.mytrivia.controller.ApplicationController;
import com.example.mytrivia.model.Question;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    ArrayList<Question> questionArrayList = new ArrayList<>();
    final String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    public List<Question> getQuestion(final AnswerListAsyncResponse callback) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            Question question = new Question(response.getJSONArray(i).getString(0), response.getJSONArray(i).getBoolean(1));
                            questionArrayList.add(question);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if (callback != null) {
                        callback.processFinished(questionArrayList);
                    }
                },
                error -> Log.d("Error", "onErrorResponse: " + "Unable to retrieve data"));

        ApplicationController.getInstance().addToRequestQueue(jsonArrayRequest);

        return questionArrayList;
    }
}

