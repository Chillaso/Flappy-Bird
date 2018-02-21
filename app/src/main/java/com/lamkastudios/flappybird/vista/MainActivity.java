package com.lamkastudios.flappybird.vista;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.lamkastudios.flappybird.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.jugar).setOnClickListener(this);
        findViewById(R.id.opciones).setOnClickListener(this);
        findViewById(R.id.record).setOnClickListener(this);
    }

    @Override
    public void onClick(View b)
    {
        if(b.getId()==R.id.jugar)
        {
            startActivity(new Intent(getApplicationContext(),Juego.class));
        }
        else if(b.getId()==R.id.opciones)
        {
            startActivity(new Intent(getApplicationContext(),Opciones.class));
        }
        else if(b.getId()==R.id.record)
        {
            SharedPreferences prefs = getSharedPreferences("puntuaciones",MODE_PRIVATE);
            Toast.makeText(this, "Su record es: "+prefs.getInt("record",0), Toast.LENGTH_SHORT).show();
        }
    }
}
