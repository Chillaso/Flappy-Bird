package com.lamkastudios.flappybird.Sprites;
//Created by chillaso All rights reserved.


import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Replay {

    private float x,y,width,height;
    private Bitmap btp;

    public Replay(Bitmap btp, float x, float y)
    {
        this.btp = btp;
        this.x=x;
        this.y=y;
        width = btp.getWidth();
        height=btp.getHeight();
    }

    public void onDraw(Canvas c)
    {
        c.drawBitmap(btp,x,y,null);
    }

    public boolean isDentro(float x1, float y1)
    {
        if(x1 > x && x1 < x+width && y1 > y && y1 < y+height)
            return true;
        else
            return false;
    }
}


