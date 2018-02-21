package com.lamkastudios.flappybird.util;
//Created by chillaso All rights reserved.


import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;

public class Sonidos {

    private static Sonidos s;
    private static Activity m;
    private static SoundPool sound;
    private static int wing;
    private static int hit;
    private static int point;

    private Sonidos(Activity m) {
        this.m=m;
        sound = new SoundPool.Builder().setMaxStreams(2).build();
        cargarSonidos();
    }

    public static Sonidos SonidoBuilder(Activity m)
    {
        if(s==null)
            s = new Sonidos(m);
        return s;
    }

    public void playWing()
    {
        sound.play(wing,1,1,1,0,1);
    }

    public void playHit()
    {
        sound.play(hit,1,1,2,0,1);
    }

    public void playPoint()
    {
        sound.play(point,1,1,2,0,1);
    }

    private void cargarSonidos()
    {
        try
        {
            AssetManager am = m.getAssets();
            AssetFileDescriptor wingDescriptor = am.openFd("wing.ogg");
            wing = sound.load(wingDescriptor,1);
            AssetFileDescriptor choque = am.openFd("hit.ogg");
            hit = sound.load(choque,2);
            AssetFileDescriptor punto = am.openFd("point.ogg");
            point = sound.load(punto,2);
        }
        catch(IOException e)
        {
            Log.e("Error de sonido","Error al cargar el sonido "+e.getMessage());
        }
    }
}
