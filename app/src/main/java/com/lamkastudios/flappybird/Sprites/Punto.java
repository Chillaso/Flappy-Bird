package com.lamkastudios.flappybird.Sprites;
//Created by chillaso All rights reserved.


import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.lamkastudios.flappybird.Vista.GameView;

import java.util.ArrayList;

public class Punto {

    private GameView game;
    private ArrayList<Bitmap> listaPuntos;
    private int contPuntos;
    private int x, y;
    private int varPuntuacion;

    public Punto(GameView game, ArrayList<Bitmap> listaPuntos, int contPuntos)
    {
        this.game=game;
        this.listaPuntos=listaPuntos;
        this.contPuntos=contPuntos;
        x = (game.getWidth()/2);
        y = (game.getHeight()/4);
        varPuntuacion=0;
    }

    /*private void update()
    {

    }*/

    public void onDraw(Canvas c)
    {
        //Pasamos el contador a cadena
        String cont = String.valueOf(contPuntos);
        for(int i=0; i<cont.length();i++)
        {
            //Obtengo la posicion del array
            int pos = Integer.parseInt(String.valueOf(cont.charAt(i)));
            //Obtengo donde quiero pintarlo
            int medioX = x-(listaPuntos.get(pos).getWidth()/2);
            int medioY= y-(listaPuntos.get(pos).getHeight()/2);
            //Pinto
            c.drawBitmap(listaPuntos.get(pos),medioX+(listaPuntos.get(pos).getWidth()*i),medioY,null);
        }
    }

    public int getContPuntos() {
        return contPuntos;
    }

    public void setContPuntos(int contPuntos) {
        this.contPuntos = contPuntos;
    }
}
