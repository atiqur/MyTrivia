package com.example.mytrivia;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.mytrivia.data.Repository;
import com.example.mytrivia.databinding.ActivityMainBinding;
import com.example.mytrivia.model.Question;
import com.example.mytrivia.model.Score;
import com.google.android.material.snackbar.Snackbar;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int currentQuestionNumber = 0;
    private int currentScore = 0;
    private Score score;
    private ActivityMainBinding binding;
    List<Question> questionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        score = new Score(currentScore);

        questionsList = new Repository().getQuestion(questionArrayList -> {
            updateCounter(questionArrayList);
            binding.questionTextView.setText(questionArrayList.get(currentQuestionNumber).getAnswer());
        });

        updateScore();

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
            addPoints();
        } else {
            snackMessage = R.string.incorrect;
            deductPoints();
        }
        Snackbar.make(binding.questionCard, snackMessage, Snackbar.LENGTH_SHORT).show();
    }

    private void addPoints() {
        currentScore += 100;
        score.setScore(currentScore);
        updateScore();
    }

    private void deductPoints() {
        if (currentScore > 0) {
            currentScore -= 100;
            score.setScore(currentScore);
        }
        updateScore();
    }

    private void updateScore() {
        binding.currentScoreText.setText(MessageFormat.format("Score: {0}", score.getScore()));
    }

    private void updateQuestion() {
        currentQuestionNumber = (currentQuestionNumber + 1) % questionsList.size();

        String question = questionsList.get(currentQuestionNumber).getAnswer();
        binding.questionTextView.setText(question);
        updateCounter(questionsList);
    }

    private void updateCounter(List<Question> questionArrayList) {
        binding.questionNumberText.setText(MessageFormat.format("{0} / {1}", (currentQuestionNumber+1), questionArrayList.size()));
    }
}