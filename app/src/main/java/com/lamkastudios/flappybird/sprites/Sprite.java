package com.lamkastudios.flappybird.sprites;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.lamkastudios.flappybird.vista.GameView;

import java.util.ArrayList;

public class Sprite
{
    //----ATRIBUTOS JUEGO----
    private GameView game;
    private Bitmap btp;
    private int currentFrame;
    private final int FRAMES = 3;
    private int width,height;
    private boolean noche;

    //----ATRIBUTOS SPRITE---
    private float x,y;

    //----ATRIBUTOS FISICAS----
    public float velocidad=0;
    private float gravedad=1;
    public float salto=-13;
    //private float rotacion=0;

    //-----CONT PUNTOS-------
    private int punto;

    public Sprite(GameView game, Bitmap btp, boolean noche)
    {
        this.game = game;
        this.btp = btp;
        this.noche=noche;
        currentFrame=0;
        punto=0;

        //DIMENSIONES DE SPRITE
        width=btp.getWidth();
        height=btp.getHeight();

        //-----POSICIÓN DE INICIO-----
        x=game.getWidth()/2.2f;
        y=game.getHeight()/1.8f;
    }

    private void update()
    {
        //---- COLISION ----
        comprobarColision();

        //-----PUNTOS-----
        comprobarPunto();

        //----FISICAS----
        velocidad+=gravedad;
        y+=velocidad;
        //rotacion=Math.min((velocidad/10)*90,90);

        //----FRAME DE VUELO----
        currentFrame = ++currentFrame % FRAMES;

        //---BITMAP ACTUAL----
        if(!noche)
            btp = BitmapFactory.decodeResource(game.getResources(),game.BIRDRESOURCES[currentFrame]);
        else
            btp = BitmapFactory.decodeResource(game.getResources(),game.NBIRDRESOURCES[currentFrame]);
    }

    public void onDraw(Canvas c)
    {
        update();
        c.drawBitmap(btp,x,y,null);
    }

    private void comprobarColision()
    {
        //-----SUELO-----
        if(y > game.getHeight()-height || y > Suelo.y-(height/2)) {
            if(game.getSonido()!=null)
                game.getSonido().playHit();
            game.gameOver();
        }
        //----PIPES-----
        ArrayList<Pipe> pipes = game.getPipes();
        for(Pipe p : pipes)
        {
            if(isDentro(p.getX(),p.yArriba(),p.yAbajo(),p.getWidthArriba()))
            {
                if(game.getSonido()!=null)
                    game.getSonido().playHit();
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
                if(game.getSonido()!=null)
                    game.getSonido().playPoint();
            }
        }
    }

    private boolean isDentro(float x1, float yArriba, float yAbajo,float pwi)
    {
        if((x1 > x && x1 < x+width && y < yArriba) || (x1 > x && x1 < x+width && y > yAbajo)
                || (x1+pwi > x && x1+pwi < x+width && y < yArriba) || (x1+pwi > x && x1+pwi < x+width && y >= yAbajo-height/3)) {
            return true;
        }
        else
            return false;
    }

    public float getX() {
        return x;
    }

}
