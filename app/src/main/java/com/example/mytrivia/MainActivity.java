package com.example.mytrivia;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.mytrivia.data.Repository;
import com.example.mytrivia.databinding.ActivityMainBinding;
import com.example.mytrivia.model.Question;
import com.google.android.material.snackbar.Snackbar;

import java.text.MessageFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int currentQuestionNumber = 0;
    private int currentScore = 0;
    private ActivityMainBinding binding;
    List<Question> questionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        questionsList = new Repository().getQuestion(questionArrayList -> {
            binding.questionTextView.setText(questionArrayList.get(currentQuestionNumber).getAnswer());
        });

        binding.nextButton.setOnClickListener(view -> {
            updateQuestion();
        });

        binding.trueButton.setOnClickListener(view -> {
            checkAnswer(true);
            updateQuestion();
        });

        binding.falseButton.setOnClickListener(view -> {
            checkAnswer(false);
            updateQuestion();
        });
    }

    private void checkAnswer(boolean userResponse) {
        boolean answer = questionsList.get(currentQuestionNumber).isAnswerTrue();
        int snackMessage;
        if (answer == userResponse) {
            snackMessage = R.string.correct;
        } else {
            snackMessage = R.string.incorrect;
        }
        Snackbar.make(binding.questionCard, snackMessage, Snackbar.LENGTH_SHORT).show();
    }

    private void getNextQuestion() {
        currentQuestionNumber = (currentQuestionNumber + 1) % questionsList.size();
    }

    private void updateQuestion() {
        getNextQuestion();

        String question = questionsList.get(currentQuestionNumber).getAnswer();
        binding.questionTextView.setText(question);
    }


}