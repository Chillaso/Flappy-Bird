package com.lamkastudios.flappybird.Sprites;
//Created by chillaso All rights reserved.


import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.lamkastudios.flappybird.Vista.GameView;

public class Score {

    private float x,y,width,height;
    private int puntuacion;
    private Bitmap btp;
    private GameView game;

    public Score(GameView game, Bitmap btp, float x, float y) {
        this.game=game;
        this.x = x;
        this.y = y;
        this.btp = btp;
    }

    private void update()
    {
        puntuacion = game.getPunto().getContPuntos();
    }

    public void onDraw(Canvas c)
    {
        update();
        //Caja del score
        c.drawBitmap(btp,x,y,null);
        //Puntuación obtenida
        c.drawText(String.valueOf(puntuacion),x+50,y+50,null);
    }
}
