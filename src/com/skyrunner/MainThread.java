package com.skyrunner;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: trid
 * Date: 23.02.13
 * Time: 15:42
 * To change this template use File | Settings | File Templates.
 */
public class MainThread extends Thread {

    private static final String TAG = MainThread.class.getSimpleName();

    private boolean running;

    private SurfaceHolder surfaceHolder;
    private MainGamePanel mainGamePanel;

    public MainThread(SurfaceHolder surfaceHolder, MainGamePanel mainGamePanel) {
        this.surfaceHolder = surfaceHolder;
        this.mainGamePanel = mainGamePanel;
    }

    public void setRunning(boolean running){
        this.running = running;
    }

    @Override
    public void run() {
        Canvas canvas;
        long lastTime = System.currentTimeMillis() - 1;
        while (running){
            long newTime = System.currentTimeMillis();
            long delta = newTime - lastTime;

            mainGamePanel.process(delta);

            canvas = null;
            try{
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    if (canvas != null) {
                        mainGamePanel.onDraw(canvas);
                    }
                }
            } finally {
                if (canvas != null){
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            lastTime = newTime;
        }
    }
}
