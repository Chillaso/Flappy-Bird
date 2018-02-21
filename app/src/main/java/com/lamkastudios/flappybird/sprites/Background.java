package com.lamkastudios.flappybird.sprites;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.lamkastudios.flappybird.vista.GameView;


public class Background {

    private GameView game;
    private Bitmap fondo;

    private int x = 0;

    public Background(GameView game, Bitmap fondo)
    {
        this.game = game;
        this.fondo = fondo;
    }

    public void onDraw(Canvas canvas)
    {
        //Movemos el mapa
        x = x - game.VELOCIDAD;

        //Calculamos el hueco que deja el fondo
        int xGap = fondo.getWidth() - (-x);

        //Pintamos el fondo normal si no hay hueco que pintar
        if (xGap <= 0)
        {
            x = 0;
            canvas.drawBitmap(fondo, x, 0, null);
        }
        else
        {
            //Si hay hueco pintamos los el fondo primero y el fondo que rellena el hueco
            canvas.drawBitmap(fondo, x, 0, null);
            canvas.drawBitmap(fondo, xGap, 0, null);
        }
    }
}
