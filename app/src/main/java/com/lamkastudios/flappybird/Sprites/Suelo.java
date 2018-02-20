package com.lamkastudios.flappybird.Sprites;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.lamkastudios.flappybird.Vista.GameView;

public class Suelo {

    private GameView game;
    protected static Bitmap suelo;
    private int x = 0;
    protected static int y;

    public Suelo(GameView game, Bitmap suelo)
    {
        this.game = game;
        this.suelo = suelo;
    }

    public void onDraw(Canvas canvas)
    {
        //Movemos el suelo
        x = x - game.VELOCIDAD;

        //Calculamos el hueco que deja el suelo
        int xGap = suelo.getWidth() - (-x);
        //Calculamos donde posicionar el suelo
        y = game.getHeight()-suelo.getHeight();


        //Pintamos el suelo normal si no hay hueco que pintar
        if (xGap <= 0)
        {
            x = 0;
            canvas.drawBitmap(suelo, x, y, null);
        }
        else
        {
            //Si hay hueco pintamos los el fondo primero y el fondo que rellena el hueco
            canvas.drawBitmap(suelo, x, y, null);
            canvas.drawBitmap(suelo, xGap, y, null);
        }
    }
}