package com.lamkastudios.flappybird.Vista;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

public class MainActivity extends Activity {

    private GameView m;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences("puntuaciones",MODE_PRIVATE);
        //-----Layout GameView
        m = new GameView(this,getApplicationContext());
        m.setBackgroundColor(Color.BLACK);
        setContentView(m);
    }

    public SharedPreferences getPrefs() {
        return prefs;
    }
}
