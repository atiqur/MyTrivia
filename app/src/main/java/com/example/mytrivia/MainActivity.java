package com.example.mytrivia;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.mytrivia.data.AnswerListAsyncResponse;
import com.example.mytrivia.data.Repository;
import com.example.mytrivia.model.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Question> questions = new Repository().getQuestion(questionArrayList ->
                Log.d("Main", "onCreate: " + questionArrayList));
    }
}