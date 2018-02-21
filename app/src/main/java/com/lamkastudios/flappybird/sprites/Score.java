package com.lamkastudios.flappybird.sprites;
//Created by chillaso All rights reserved.


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.lamkastudios.flappybird.vista.GameView;

public class Score {

    private Bitmap btp;
    private GameView game;
    private Paint p;
    private float x,y;
    private int puntuacion,record;
    private int medalla;

    public Score(GameView game, Bitmap btp, float x, float y) {
        this.game=game;
        this.x = x;
        this.y = y;
        this.btp = btp;
        medalla=-1;
        record = game.getPrefs().getInt("record",0);

        p = new Paint();
        p.setTextSize(46);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.BLACK);

    }

    private void update()
    {
        puntuacion = game.getPunto().getContPuntos();
        if(puntuacion > 10)
            medalla = 0;
        else if(puntuacion > 25)
            medalla=1;
        else if(puntuacion > 50)
            medalla=2;
        else if(puntuacion > 99)
            medalla=3;
    }

    public void onDraw(Canvas c)
    {
        update();
        //CAJA DE SCORE
        c.drawBitmap(btp,x,y,null);

        //NUMEROS PUNTUACION Y RECORD
        c.drawText(String.valueOf(puntuacion),x+(btp.getWidth()/1.3f),y+(btp.getHeight()/2.35f),p);
        c.drawText(String.valueOf(record),x+(btp.getWidth()/1.3f),y+(btp.getHeight()/1.7f),p);
        if(medalla>-1)
            c.drawBitmap(game.getMedallas().get(medalla),x+(btp.getWidth()/7.78f),y+(btp.getHeight()/2.45f),null);

    }
}
