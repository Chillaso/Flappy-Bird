package com.lamkastudios.flappybird.vista;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.lamkastudios.flappybird.R;
import com.lamkastudios.flappybird.dao.GuardarPuntuacion;
import com.lamkastudios.flappybird.logic.GameLoop;
import com.lamkastudios.flappybird.sprites.Background;
import com.lamkastudios.flappybird.sprites.Pipe;
import com.lamkastudios.flappybird.sprites.Punto;
import com.lamkastudios.flappybird.sprites.Replay;
import com.lamkastudios.flappybird.sprites.Score;
import com.lamkastudios.flappybird.sprites.Sprite;
import com.lamkastudios.flappybird.sprites.Suelo;
import com.lamkastudios.flappybird.util.Sonidos;

import java.util.ArrayList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
    //----ATRIBUTOS VIEW------
    private GameLoop loop;
    private Canvas c;
    private Juego m;
    private SharedPreferences prefs;

    //-----CONSTANTES DEL JUEGO
    public final int[] BIRDRESOURCES = new int[]{R.drawable.abajo,R.drawable.medio,R.drawable.arriba};
    public final int[] NBIRDRESOURCES = new int[]{R.drawable.nabajo,R.drawable.nmedio,R.drawable.narriba};
    private final double ANCHO =0.1;
    private final double ALTO=0.1;
    public final static int GAP = 500;
    public static float VELOCIDAD = 10;

    //----FLAGS-----
    private boolean play;
    private boolean gameOver;

    //----SPRITES------
    private Background fondo;
    private Sprite sprite;
    private Suelo suelo;
    private Bitmap carga;
    private Score score;
    private Replay replay;
    private Punto punto;
    private  ArrayList<Pipe> pipes;
    private ArrayList<Bitmap> puntos;
    private ArrayList<Bitmap> medallas;

    //----SONIDOS-----
    private Sonidos sonido;


    public GameView(Juego m, Context context)
    {
        super(context);
        this.m=m;
        getHolder().addCallback(this);

        play=false;
        gameOver=false;
        pipes = new ArrayList<>();
        puntos = new ArrayList<>();
        medallas= new ArrayList<>();
        prefs = m.getSharedPreferences("puntuaciones",Context.MODE_PRIVATE);
        VELOCIDAD = 10;
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
        //----PANTALLA CARGA----
        if(!play)
            canvas.drawBitmap(carga,getWidth()/2-carga.getWidth()/2,getHeight()/2-carga.getHeight()/2,null);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(play && !gameOver)
        {
            //----TAP PLAY----
            sprite.velocidad=sprite.salto;

            if(sonido!=null)
            {
                sonido.playWing();
            }
        }
        //----FIRST CLICK----
        else if(!play)
        {
            loop.start();
            play=true;
        }
        //-----GAMEOVER AND REPLAY----
        if(gameOver && replay.isDentro(event.getX(),event.getY()))
            rePlay();
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        cargarSprites(prefs.getBoolean("modonoche",false));
        cargarMapa(prefs.getBoolean("modonoche",false));
        cargarSonidos(prefs.getBoolean("musica",true));
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


    private void cargarSprites(boolean noche)
    {
        //------BIRD-------
        Bitmap btp;
        if(!noche)
            btp = BitmapFactory.decodeResource(getResources(), R.drawable.abajo);

        else
            btp = BitmapFactory.decodeResource(getResources(), R.drawable.nabajo);

        int ancho = (int) (getWidth() * ANCHO);
        int alto = (int) (getHeight() * ALTO);
        sprite = new Sprite(this, Bitmap.createScaledBitmap(btp, ancho, alto, true),noche);


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

        //----FINAL----
        Bitmap btpscore = BitmapFactory.decodeResource(getResources(),R.drawable.score);
        int xx = (getWidth()/2)-(btpscore.getWidth()/2);
        int yy = (getHeight()/2)-btpscore.getHeight()/2;
        score = new Score(this,btpscore,xx,yy);

        float xxR = (getWidth()/2)-(btpscore.getWidth()/2)+(btpscore.getWidth()/4);
        float yyR = (getHeight()/2)-btpscore.getHeight()/2+(btpscore.getHeight());
        replay = new Replay(BitmapFactory.decodeResource(getResources(),R.drawable.replay),xxR,yyR);

        //----MEDALLA----
        Bitmap bronce = BitmapFactory.decodeResource(getResources(),R.drawable.bronce);
        Bitmap plata = BitmapFactory.decodeResource(getResources(),R.drawable.plata);
        Bitmap oro = BitmapFactory.decodeResource(getResources(),R.drawable.oro);
        Bitmap platino = BitmapFactory.decodeResource(getResources(),R.drawable.platino);
        medallas.add(bronce);
        medallas.add(plata);
        medallas.add(oro);
        medallas.add(platino);
    }

    private void cargarMapa(boolean noche)
    {
        //------FONDO------
        Bitmap btp;
        if(!noche)
            btp = BitmapFactory.decodeResource(getResources(),R.drawable.bgd);
        else
            btp = BitmapFactory.decodeResource(getResources(),R.drawable.nbackground);
        Bitmap background = Bitmap.createScaledBitmap(btp,this.getWidth(),this.getHeight(),true);
        fondo = new Background(this,background);

        //-----SUELO-------
        Bitmap btpSuelo = BitmapFactory.decodeResource(getResources(),R.drawable.suelo);
        Bitmap sueloRedim = Bitmap.createScaledBitmap(btpSuelo,this.getWidth(),btpSuelo.getHeight(),true);
        suelo = new Suelo(this,sueloRedim);

        //-----PIPES-------
        Bitmap pDown;
        Bitmap pUp;

        if(!noche)
        {
            pDown = BitmapFactory.decodeResource(getResources(),R.drawable.pipeup);
            pUp = BitmapFactory.decodeResource(getResources(),R.drawable.pipedown);
        }
        else
        {
            pDown = BitmapFactory.decodeResource(getResources(),R.drawable.nup);
            pUp = BitmapFactory.decodeResource(getResources(),R.drawable.ndown);
        }

        Bitmap pAbajo = Bitmap.createScaledBitmap(pDown, pDown.getWidth(), getHeight() / 2, true);
        Bitmap pArriba = Bitmap.createScaledBitmap(pUp, pUp.getWidth(), this.getHeight() / 2, true);


        float distancia = sprite.getX()*3;

        pipes.add(new Pipe(this, pAbajo, pArriba,distancia,pUp.getHeight()-getHeight()/2f));
        pipes.add(new Pipe(this, pAbajo, pArriba,distancia+GAP,pUp.getHeight()-getHeight()/2.2f));
        pipes.add(new Pipe(this, pAbajo, pArriba,distancia+GAP*2,pUp.getHeight()-getHeight()/2.123f));

        //----PANTALLA CARGA----
        carga = BitmapFactory.decodeResource(getResources(),R.drawable.carga);
    }

    private void cargarSonidos(boolean cargar)
    {
        if(cargar)
            sonido = Sonidos.SonidoBuilder(m);
    }

    public void gameOver()
    {
        loop.interrupt();

        score.onDraw(c);
        replay.onDraw(c);
        gameOver=true;
        GuardarPuntuacion guardar = new GuardarPuntuacion(m,punto.getContPuntos());
        guardar.execute();
        VELOCIDAD=10;
    }

    public void rePlay()
    {
        m.finish();
        m.startActivity(m.getIntent());
    }

    public ArrayList<Pipe> getPipes() {
        return pipes;
    }

    public ArrayList<Bitmap> getMedallas() {
        return medallas;
    }

    public Punto getPunto() {
        return punto;
    }

    public Sonidos getSonido() {
        return sonido;
    }

    public SharedPreferences getPrefs() {
        return prefs;
    }
}
