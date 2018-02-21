package com.lamkastudios.flappybird.logic;

import android.graphics.Canvas;

import com.lamkastudios.flappybird.vista.GameView;

public class GameLoop extends Thread {

    private static final long FPS = 30; //Velocidad 10 veces/s
    private GameView view;

    public GameLoop(GameView view)
    {
        this.view=view;
    }

    @Override
    public void run()
    {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;

        while(!interrupted())
        {
            Canvas c = null;
            startTime = System.currentTimeMillis();

            try
            {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder())
                {
                    view.postInvalidate();
                }
            }

            finally
            {
                if(c!=null)
                    view.getHolder().unlockCanvasAndPost(c);
            }

            sleepTime = ticksPS - (System.currentTimeMillis()-startTime);

            try
            {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
                this.interrupt();
            }
        }
    }
}
