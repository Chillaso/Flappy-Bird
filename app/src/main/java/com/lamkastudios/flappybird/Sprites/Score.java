package com.lamkastudios.flappybird.Sprites;
//Created by chillaso All rights reserved.


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.lamkastudios.flappybird.Vista.GameView;

public class Score {

    private float x,y,width,height;
    private int puntuacion;
    private Bitmap btp;
    private GameView game;
    private Paint p;

    public Score(GameView game, Bitmap btp, float x, float y) {
        this.game=game;
        this.x = x;
        this.y = y;
        this.btp = btp;
        p = new Paint();
        p.setTextSize(42);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.BLACK);
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
        c.drawText(String.valueOf(puntuacion),x+(btp.getWidth()/1.3f),y+(btp.getHeight()/2.4f),p);
    }
}
