package com.lamkastudios.flappybird.Vista;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.lamkastudios.flappybird.Logic.GameLoop;
import com.lamkastudios.flappybird.R;
import com.lamkastudios.flappybird.Sprites.Background;
import com.lamkastudios.flappybird.Sprites.Pipe;
import com.lamkastudios.flappybird.Sprites.Punto;
import com.lamkastudios.flappybird.Sprites.Sprite;
import com.lamkastudios.flappybird.Sprites.Suelo;

import java.io.IOException;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
    private GameLoop loop;
    private Canvas c;
    private MainActivity m;
    private final double ANCHO =0.1;
    private final double ALTO=0.1;
    public final int[] BIRDRESOURCES = new int[]{R.drawable.abajo,R.drawable.medio,R.drawable.arriba};

    public final static int GAP = 500;
    public final static int VELOCIDAD = 10;
    private boolean play;
    private boolean gameOver;

    private Background fondo;
    private Sprite sprite;
    private Suelo suelo;
    private Punto punto;
    private  ArrayList<Pipe> pipes;
    private ArrayList<Bitmap> puntos;
    private SoundPool sound;
    private int sonidoAla;
    private int sonidoChoque;
    private int sonidoPunto;

    public GameView(MainActivity m,Context context)
    {
        super(context);
        this.m=m;
        getHolder().addCallback(this);

        play=false;
        gameOver=false;
        pipes = new ArrayList<>();
        puntos = new ArrayList<>();
        sonidoAla =-1;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.c = canvas;

        //----DRAW GAME----
        fondo.onDraw(canvas);
        for (Pipe p : pipes)
            p.onDraw(canvas);
        suelo.onDraw(canvas);
        sprite.onDraw(canvas);
        punto.onDraw(canvas);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(play)
        {
            //----TAP PLAY----
            sprite.setY((int) (sprite.getY() - (sprite.getYSPEED() * sprite.getYSPEED() * 0.7)));
            if(sonidoAla != -1)
            {
                sound.play(sonidoAla,1,1,1,0,1);
            }
        }
        //----FIRST CLICK----
        else
        {
            loop.start();
            play=true;
        }
        if(gameOver)
            rePlay();
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        cargarSprites();
        cargarMapa();
        cargarSonidos();
        loop = new GameLoop(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        loop.interrupt();
        loop=null;
    }

    private void cargarSprites()
    {
        //------BIRD-------
        //Cremaos un sprite mid, luego iremos cambiando en el update
        Bitmap btp = BitmapFactory.decodeResource(getResources(),R.drawable.medio);
        int ancho = (int) (getWidth()*ANCHO);
        int alto = (int) (getHeight()*ALTO);
        sprite = new Sprite(this,Bitmap.createScaledBitmap(btp,ancho,alto,true));

        //---PUNTOS-----
        Bitmap cero = BitmapFactory.decodeResource(getResources(),R.drawable.p0);
        Bitmap uno = BitmapFactory.decodeResource(getResources(),R.drawable.p1);
        Bitmap dos = BitmapFactory.decodeResource(getResources(),R.drawable.p2);
        Bitmap tres = BitmapFactory.decodeResource(getResources(),R.drawable.p3);
        Bitmap cuatro = BitmapFactory.decodeResource(getResources(),R.drawable.p4);
        Bitmap cinco = BitmapFactory.decodeResource(getResources(),R.drawable.p5);
        Bitmap seis = BitmapFactory.decodeResource(getResources(),R.drawable.p6);
        Bitmap siete = BitmapFactory.decodeResource(getResources(),R.drawable.p7);
        Bitmap ocho = BitmapFactory.decodeResource(getResources(),R.drawable.p8);
        Bitmap nueve = BitmapFactory.decodeResource(getResources(),R.drawable.p9);
        puntos.add(cero);
        puntos.add(uno);
        puntos.add(dos);
        puntos.add(tres);
        puntos.add(cuatro);
        puntos.add(cinco);
        puntos.add(seis);
        puntos.add(siete);
        puntos.add(ocho);
        puntos.add(nueve);
        punto = new Punto(this,puntos,0);
    }

    private void cargarMapa()
    {
        //------FONDO------
        Bitmap btp = BitmapFactory.decodeResource(getResources(),R.drawable.bgd);
        Bitmap background = Bitmap.createScaledBitmap(btp,this.getWidth(),this.getHeight(),true);
        fondo = new Background(this,background);

        //-----SUELO-------
        Bitmap btpSuelo = BitmapFactory.decodeResource(getResources(),R.drawable.suelo);
        Bitmap sueloRedim = Bitmap.createScaledBitmap(btpSuelo,this.getWidth(),btpSuelo.getHeight(),true);
        suelo = new Suelo(this,sueloRedim);

        //-----PIPES-------
        Bitmap pDown = BitmapFactory.decodeResource(getResources(),R.drawable.pipeup);
        Bitmap pAbajo = Bitmap.createScaledBitmap(pDown, pDown.getWidth(), getHeight() / 2, true);
        Bitmap pUp = BitmapFactory.decodeResource(getResources(),R.drawable.pipedown);
        Bitmap pArriba = Bitmap.createScaledBitmap(pUp, pUp.getWidth(), this.getHeight() / 2, true);


        float distancia = sprite.getX()*4;

        pipes.add(new Pipe(this, pAbajo, pArriba,distancia,pUp.getHeight()-getHeight()/2f));
        pipes.add(new Pipe(this, pAbajo, pArriba,distancia+GAP,pUp.getHeight()-getHeight()/2.2f));
        Log.d(TAG, "cargarMapa: "+(pUp.getHeight()-getHeight()/2.2f));
        pipes.add(new Pipe(this, pAbajo, pArriba,distancia+GAP*2,pUp.getHeight()-getHeight()/2.123f));
    }

    private void cargarSonidos()
    {
        sound = new SoundPool.Builder().setMaxStreams(2).build();

        try
        {
            AssetManager am = m.getAssets();
            AssetFileDescriptor wing = am.openFd("wing.ogg");
            sonidoAla = sound.load(wing,1);
            AssetFileDescriptor choque = am.openFd("hit.ogg");
            sonidoChoque = sound.load(choque,2);
            AssetFileDescriptor punto = am.openFd("point.ogg");
            sonidoPunto = sound.load(punto,2);
        }
        catch(IOException e)
        {
            Log.e("Error de sonido","Error al cargar el sonido "+e.getMessage());
        }
    }

    public void sonidoChoque()
    {
        sound.play(sonidoChoque,1,1,2,0,1);
    }

    public void sonidoPunto()
    {
        sound.play(sonidoPunto,1,1,2,0,1);
    }

    public void gameOver()
    {
        loop.interrupt();
        Bitmap gameover = BitmapFactory.decodeResource(getResources(),R.drawable.gameover);

        int ancho = (getWidth()/2)-(gameover.getWidth()/2);
        int alto = (getHeight()/2)-gameover.getHeight()/2;

        c.drawBitmap(gameover,ancho,alto,null);
        gameOver=true;
        //Espero un poco para que no sea un instaplay
    }

    public void rePlay()
    {
        m.finish();
        m.startActivity(m.getIntent());
    }

    public ArrayList<Pipe> getPipes() {
        return pipes;
    }

    public Punto getPunto() {
        return punto;
    }
}
