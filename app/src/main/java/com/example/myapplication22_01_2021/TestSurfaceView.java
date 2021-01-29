package com.example.myapplication22_01_2021;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class TestSurfaceView extends SurfaceView implements SurfaceHolder.Callback, MyListener {
    private MyListener radChangeListener;
    private float rad;
    Thread thread;



    public void setRad(float rad) {
        this.rad = rad;
    }




    public TestSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        thread = new DrawThread();
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        thread.interrupt();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEvent(float value) {
        setRad(value);

    }

    class DrawThread extends Thread {
        Paint paint = new Paint();

        @Override
        public void run() {
            while (!isInterrupted()){
                Canvas canvas = null;
                try {
                    canvas = getHolder().lockCanvas();
                    if (canvas != null) {
                        canvas.drawColor(Color.BLUE);
                        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, rad, paint);

                    }
                }
                finally {
                    if (canvas != null){
                        getHolder().unlockCanvasAndPost(canvas);
                    }
                }
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}