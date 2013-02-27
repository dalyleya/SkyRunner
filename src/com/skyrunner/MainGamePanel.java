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

    PlayerCharacter character;

    Bitmap obstacleBitmap;

    private List<StaticObstacle> obstacles = new ArrayList<StaticObstacle>();

    private float screenHeight;
    private final int screenWidth;
    private int hits;
    private long obstacleCreationTimer;
    private long cleaningTimer;

    public MainGamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        setFocusable(true);

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        screenWidth = display.getWidth();
        screenHeight = display.getHeight();

        GlobalState globalState = GlobalState.getInstance();
        globalState.setScreenHeight(screenHeight);
        globalState.setScreenWidth(screenWidth);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.character);
        character = new PlayerCharacter(bitmap, (screenWidth - bitmap.getWidth())/2, (int) ((screenHeight - bitmap.getHeight())/5));
        obstacleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.obstacle);

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
        canvas.drawText(String.format("Hits: %d", hits), 10, 25, paint);
        super.onDraw(canvas);
    }

    public void process(long delta) {
        for (StaticObstacle obstacle: obstacles){

            obstacle.process(delta);
            detectCollision(obstacle, character);

        }

        Random random = new Random();

        obstacleCreationTimer += delta;

        if (obstacleCreationTimer > 50){
            if (random.nextInt(50) == 0){
                obstacles.add(new StaticObstacle(obstacleBitmap, random.nextInt(screenWidth + 1000) - 500, screenHeight));
            }
            obstacleCreationTimer -= 50; //No, we can't use tricky mathematics everywhere :(
        }

        cleaningTimer += delta;

        if (cleaningTimer > 1000){
            cleaningTimer -= 1000;
            Iterator iterator =  obstacles.iterator();
            while (iterator.hasNext()) {
                StaticObstacle obstacle = (StaticObstacle)iterator.next();
                if ( obstacle.getY() <= -obstacle.getScaledBitmap().getHeight() ){
                    iterator.remove();
                }

            }

        }
    }

    private void detectCollision(StaticObstacle obstacle, PlayerCharacter character) {
        if (obstacle.isCollided()) return;

        float obstacleLeft = obstacle.getX();
        float obstacleRight = obstacleLeft + obstacle.getScaledBitmap().getWidth();
        float obstacleTop = obstacle.getY();
        float obstacleBottom = obstacleTop + obstacle.getScaledBitmap().getHeight();

        float characterLeft = character.getX();
        float characterRight = characterLeft + character.getBitmap().getWidth();
        float characterTop = character.getY();
        float characterBottom = characterTop + character.getBitmap().getHeight();

        if (obstacleLeft > characterRight) return;
        if (obstacleRight < characterLeft) return;
        if (obstacleTop > characterBottom) return;
        if (obstacleBottom < characterTop) return;

        obstacle.setCollided(true);
        Log.d(TAG, "Collided");

        hits++;
    }

    public void drawObstacles(Canvas canvas){
        for (StaticObstacle obstacle: obstacles){
            canvas.drawBitmap(obstacle.getScaledBitmap(), obstacle.getX(), obstacle.getY(), null);
        }
    }

    private class MySensorListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];

            float angle = calculateAngle(x);
            GlobalState.getInstance().setDx(60 * angle / 90);
            character.setAngle(angle);
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
