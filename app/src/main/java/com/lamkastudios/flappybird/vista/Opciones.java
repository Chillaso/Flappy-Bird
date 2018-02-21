package com.lamkastudios.flappybird.vista;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.lamkastudios.flappybird.R;

public class Opciones extends AppCompatActivity implements View.OnClickListener, Switch.OnCheckedChangeListener{

    private Switch musica,noche;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);

        musica = findViewById(R.id.musica);
        noche = findViewById(R.id.modoNoche);
        musica.setOnCheckedChangeListener(this);
        noche.setOnCheckedChangeListener(this);
        findViewById(R.id.deleteRecord).setOnClickListener(this);

        prefs = getSharedPreferences("puntuaciones",MODE_PRIVATE);
        musica.setChecked(prefs.getBoolean("musica",true));
        noche.setChecked(prefs.getBoolean("modonoche",false));
    }

    @Override
    public void onClick(View b)
    {
        if(b.getId()==R.id.deleteRecord)
        {
            SharedPreferences.Editor e = prefs.edit();
            e.putInt("record",0);
            e.apply();
            Toast.makeText(this, "Se ha eliminado el record actual", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton boton, boolean b)
    {
        SharedPreferences.Editor e = prefs.edit();
        if(boton.getId()==R.id.musica)
        {
            if(b)
                e.putBoolean("musica",true);
            else
                e.putBoolean("musica",false);

        }
        else
        {
            if(b)
                e.putBoolean("modonoche",true);
            else
                e.putBoolean("modonoche",false);
        }
        e.apply();
    }
}
