package com.example.mytrivia;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;

import com.example.mytrivia.data.Repository;
import com.example.mytrivia.databinding.ActivityMainBinding;
import com.example.mytrivia.model.Question;

import java.text.MessageFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final int currentQuestionNumber = 0;
    private int currentScore = 0;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.questionTextView.setText("Data binding is working!!!");
        List<Question> questions = new Repository().getQuestion(questionArrayList ->
                Log.d("Main", "onCreate: " + questionArrayList.get(0).getAnswer()));
    }
}