package com.example.mytrivia;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.mytrivia.data.Repository;
import com.example.mytrivia.databinding.ActivityMainBinding;
import com.example.mytrivia.model.Question;
import com.example.mytrivia.model.Score;
import com.example.mytrivia.util.Prefs;
import com.google.android.material.snackbar.Snackbar;

import java.text.MessageFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int currentQuestionNumber = 0;
    private int currentScore = 0;
    private int highestScore = 0;
    private Score score;
    private Prefs prefs;
    private ActivityMainBinding binding;
    List<Question> questionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        score = new Score(currentScore);
        prefs = new Prefs(MainActivity.this);
        updateScore();
        highestScore = prefs.getHighestScore();
        currentQuestionNumber = prefs.getState();

        binding.highestScoreText.setText(MessageFormat.format("Highest score: {0}", highestScore));

        questionsList = new Repository().getQuestion(questionArrayList -> {
            updateCounter(questionArrayList);
            binding.questionTextView.setText(questionArrayList.get(currentQuestionNumber).getAnswer());
        });

        binding.nextButton.setOnClickListener(view -> updateQuestion());

        binding.trueButton.setOnClickListener(view -> checkAnswer(true));

        binding.falseButton.setOnClickListener(view -> checkAnswer(false));
    }

    private void checkAnswer(boolean userResponse) {
        boolean answer = questionsList.get(currentQuestionNumber).isAnswerTrue();
        int snackMessage;
        if (answer == userResponse) {
            snackMessage = R.string.correct;
            addPoints();
            fadeAnimation();
        } else {
            snackMessage = R.string.incorrect;
            deductPoints();
            shakeAnimation();
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

    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake_animation);
        binding.questionCard.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionTextView.setTextColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionTextView.setTextColor(Color.WHITE);
                updateQuestion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void fadeAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(300);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        binding.questionCard.setAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionTextView.setTextColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionTextView.setTextColor(Color.WHITE);
                updateQuestion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (currentScore > highestScore) {
            highestScore = currentScore;
            prefs.setHighestScore(highestScore);
        }
        prefs.setState(currentQuestionNumber);
    }
}