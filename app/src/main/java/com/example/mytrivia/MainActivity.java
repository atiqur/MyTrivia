package com.example.mytrivia;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.mytrivia.data.Repository;
import com.example.mytrivia.databinding.ActivityMainBinding;
import com.example.mytrivia.model.Question;

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
            getNextQuestion();
            updateQuestion();
        });
    }

    private void getNextQuestion() {
        currentQuestionNumber = (currentQuestionNumber + 1) % questionsList.size();
    }

    private void updateQuestion() {
        String question = questionsList.get(currentQuestionNumber).getAnswer();
        binding.questionTextView.setText(question);
    }
}