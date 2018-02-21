package com.lamkastudios.flappybird.DAO;
//Created by chillaso All rights reserved.


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.lamkastudios.flappybird.Vista.MainActivity;

public class GuardarPuntuacion extends AsyncTask<Void,Integer,Boolean>{

    private MainActivity m;
    private int puntuacion;

    public GuardarPuntuacion(MainActivity m,int puntuacion) {
        this.m=m;
        this.puntuacion=puntuacion;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        save();
        return null;
    }

    private boolean save()
    {
        try
        {
            SharedPreferences prefs = m.getSharedPreferences("puntuaciones", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            int record = prefs.getInt("record",0);
            if(puntuacion > record)
            {

                editor.putInt("record",puntuacion);
            }
            editor.apply();
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }
}
