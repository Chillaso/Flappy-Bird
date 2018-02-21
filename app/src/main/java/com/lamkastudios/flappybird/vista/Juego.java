package com.lamkastudios.flappybird.vista;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Juego extends Activity {

    private GameView m;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //-----FULL SCREEN
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        prefs = getSharedPreferences("puntuaciones",MODE_PRIVATE);
        //-----Layout GameView
        m = new GameView(this,getApplicationContext());
        m.setBackgroundColor(Color.BLACK);
        setContentView(m);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
