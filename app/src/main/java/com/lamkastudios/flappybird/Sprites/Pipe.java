package com.lamkastudios.flappybird.Sprites;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.lamkastudios.flappybird.Vista.GameView;

import java.util.Random;

public class Pipe {

    private Bitmap btp;
    private Bitmap btp2;
    private float x,y;
    private float width;

    private int screenHeight;
    private GameView game;
    private Random r;
    private float realGAP = 2.2f;
    private boolean punto;

    public Pipe(GameView game,Bitmap btp, Bitmap btp2, float x, float y)
    {
        this.game=game;
        this.btp = btp;
        this.btp2 = btp2;
        this.x = x;
        this.y = y;

        punto = false;
        width=btp.getWidth();

        r= new Random();
        screenHeight = game.getHeight();
    }

    private void update()
    {
        //Mueve la pipe a la misma velocidad que el fondo
        x-=GameView.VELOCIDAD;

        //Despues de las tres primeras, las recicla y les asigna una nueva X y una nueva Y
        for(Pipe p : game.getPipes())
        {
            if(p.getX()+ GameView.GAP /2<0)
            {
                p.x= GameView.GAP *2.5f;//2.5f; //Le asigna la X fuera de la pantalla
                //-------------EN CAMBIO-----------------------
                p.y = r.nextInt(GameView.GAP)- GameView.GAP /2;
                if(-(GameView.GAP /realGAP)+p.y>0)
                    p.y=0;
                //---------------------------------------------
                //Reinicio que pueda ser punteada
                punto=false;
                if(realGAP <=2.5)
                    realGAP+=0.1f;
            }
        }
    }

    public void onDraw(Canvas c)
    {
        update();
        //Pipe ABAJO
        c.drawBitmap(btp,x,((screenHeight/2)+(GameView.GAP /realGAP))+y,null);
        //Pipe ARRIBA
        c.drawBitmap(btp2,x,-(GameView.GAP /realGAP)+y,null);
    }

    public float getX() {
        return x;
    }

    public float yArriba()
    {
        return (-(GameView.GAP /realGAP)+y)+btp2.getHeight();
    }

    public float yAbajo()
    {
        return (((screenHeight/2)+(GameView.GAP /realGAP))+y);
    }

    public float getHueco()
    {
        return x+btp.getWidth();
    }

    public boolean isPunto() {
        return punto;
    }

    public void setPunto(boolean punto) {
        this.punto = punto;
    }

    public float getWidthArriba() {
        return width;
    }
}


