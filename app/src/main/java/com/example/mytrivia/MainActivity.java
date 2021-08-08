package com.example.mytrivia;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.mytrivia.controller.ApplicationController;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> Log.d("Main", "onResponse: " + response.toString()),
                error -> Log.d("Fetch Error", "onErrorResponse: " + "Unable to retrieve data"));

        ApplicationController.getInstance().addToRequestQueue(jsonArrayRequest);
    }
}