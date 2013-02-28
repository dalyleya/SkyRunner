package com.skyrunner;

import android.content.Context;
import android.graphics.*;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.*;
import com.example.R;
import com.skyrunner.core.Game;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: trid
 * Date: 23.02.13
 * Time: 15:39
 * To change this template use File | Settings | File Templates.
 */
public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private static final String TAG = MainGamePanel.class.getSimpleName();

    private float screenHeight;
    private final int screenWidth;
    private final Game game;

    public MainGamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        screenWidth = display.getWidth();
        screenHeight = display.getHeight();

        game = new Game(screenWidth, screenHeight, this);

        thread = new MainThread(getHolder(), this);

        setFocusable(true);

        GlobalState globalState = GlobalState.getInstance();
        globalState.setScreenHeight(screenHeight);
        globalState.setScreenWidth(screenWidth);



        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(new MySensorListener(), accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;

        while (retry){
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            if (event.getY() > getHeight() - 50){
            }
            else {
                Log.d(TAG, String.format("Coords: x = %f; y = %f", event.getX(), event.getY()));
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        PlayerCharacter character = game.getCharacter();
        Bitmap bitmap = character.getBitmap();
        Matrix matrix = new Matrix();
        matrix.setRotate(character.getAngle(), bitmap.getWidth()/2, bitmap.getHeight()/4);

        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        canvas.drawARGB(255, 0, 0, 0);
        canvas.drawBitmap(rotatedBitmap, character.getX(), character.getY(), null);
        drawObstacles(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(20);
        canvas.drawText(String.format("Hits: %d", game.getHits()), 10, 25, paint);
        canvas.drawText(String.format("Distance: %f.2", game.getDistance()), screenWidth - 175, 25, paint);
        super.onDraw(canvas);
    }

    public void process(long delta) {
        game.process(delta);
    }

    public void drawObstacles(Canvas canvas){
        for (StaticObstacle obstacle: game.getObstacles()){
            canvas.drawBitmap(obstacle.getScaledBitmap(), obstacle.getX(), obstacle.getY(), null);
        }
    }

    private class MySensorListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];

            float angle = calculateAngle(x);
            GlobalState.getInstance().setDx(60 * angle / 90);
            game.getCharacter().setAngle(angle);
        }

        private float calculateAngle(float x) {
            float angle = 90 * (x / SensorManager.GRAVITY_EARTH);
            return -angle;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    }
}
