package com.example.mytrivia.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    private static final String HIGHEST_SCORE = "highest_score";
    private static final String STATE = "state";
    private SharedPreferences preferences;

    public Prefs(Activity context) {
        this.preferences = context.getPreferences(Context.MODE_PRIVATE);
    }

    public int getHighestScore() {
        return preferences.getInt(HIGHEST_SCORE, 0);
    }

    public void setHighestScore(int highestScore) {
        preferences.edit().putInt(HIGHEST_SCORE, highestScore).apply();
    }

    public int getState() {
        return preferences.getInt(STATE, 0);
    }

    public void setState(int state) {
        preferences.edit().putInt(STATE, state).apply();
    }
}
