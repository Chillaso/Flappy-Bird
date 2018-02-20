package com.lamkastudios.flappybird.Sprites;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.lamkastudios.flappybird.Vista.GameView;

import java.util.ArrayList;

public class Sprite
{
    //----ATRIBUTOS JUEGO----
    private GameView game;
    private Bitmap btp;
    private int currentFrame;
    private final int FRAMES = 3;
    private int width,height;

    //----ATRIBUTOS SPRITE---
    private float x,y;
    protected final int YSPEED=15;
    public static boolean choque;

    //-----CONT PUNTOS-------
    private int punto;

    public Sprite(GameView game, Bitmap btp)
    {
        this.game = game;
        this.btp = btp;
        choque=false;
        currentFrame=0;

        //DIMENSIONES DE SPRITE
        width=btp.getWidth();
        height=btp.getHeight();

        //-----POSICIÓN DE INICIO-----
        x=game.getWidth()/4;
        y=game.getHeight()/2;

        punto=0;

    }

    private void update()
    {
        //---- COLISION ----
        comprobarColision();

        //-----PUNTOS-----
        comprobarPunto();

        //----FISICAS----
        y+= YSPEED;

        //----FRAME DE VUELO----
        currentFrame = ++currentFrame % FRAMES;
        //---BITMAP ACTUAL----
        btp = BitmapFactory.decodeResource(game.getResources(),game.BIRDRESOURCES[currentFrame]);
    }

    public void onDraw(Canvas c)
    {
        update();
        c.drawBitmap(btp,x,y,null);
    }

    private void comprobarColision()
    {
        //Suelo y techo
        if(y > game.getHeight()-height || y > Suelo.y-(height/2)) {
            game.sonidoChoque();
            game.gameOver();
        }

        ArrayList<Pipe> pipes = game.getPipes();
        for(Pipe p : pipes)
        {
            if(isDentro(p.getX(),p.yArriba(),p.yAbajo(),p.getWidthArriba()))
            {
                //Reproducir sonidos
                game.sonidoChoque();
                game.gameOver();
            }
        }
    }

    private void comprobarPunto()
    {
        ArrayList<Pipe> pipes = game.getPipes();
        for (Pipe p : pipes)
        {
            if (x > p.getHueco() && !p.isPunto())
            {
                punto++;
                p.setPunto(true);
                game.getPunto().setContPuntos(punto);
                game.sonidoPunto();
            }
        }
    }

    private boolean isDentro(float x1, float yArriba, float yAbajo,float pwi)
    {
        if((x1 > x && x1 < x+width && y < yArriba) || (x1 > x && x1 < x+width && y > yAbajo)
                || (x1+pwi > x && x1+pwi < x+width && y < yArriba) || (x1+pwi > x && x1+pwi < x+width && y > yAbajo-height))
            return true;
        else
            return false;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getYSPEED() {
        return YSPEED;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
