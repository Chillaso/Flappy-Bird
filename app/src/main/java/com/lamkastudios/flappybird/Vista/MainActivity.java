package com.lamkastudios.flappybird.Vista;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.lamkastudios.flappybird.Vista.GameView;

public class MainActivity extends Activity {

    private GameView m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m = new GameView(this,getApplicationContext());
        m.setBackgroundColor(Color.BLACK);
        setContentView(m);
    }
}
