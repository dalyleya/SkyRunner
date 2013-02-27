package com.skyrunner;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import java.io.ByteArrayOutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: trid
 * Date: 24.02.13
 * Time: 1:46
 * To change this template use File | Settings | File Templates.
 */
public class StaticObstacle {

    private final int minX = - 500;
    private final int maxX = 500;

    private final int maxY = 100;

    private Bitmap originalBitmap;
    private Bitmap scaledBitmap;
    private float x, y;
    private float speed = 20.0f;
    private boolean collided = false;

    public StaticObstacle(Bitmap originalBitmap, float x, float y) {
        this.originalBitmap = originalBitmap;
        this.scaledBitmap = originalBitmap;
        this.x = x;
        this.y = y;
    }

    public Bitmap getScaledBitmap() {
        return scaledBitmap;
    }

    public void setScaledBitmap(Bitmap scaledBitmap) {
        this.scaledBitmap = scaledBitmap;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void process(long delta) {
        y -= (speed * delta)/1000.;
        x -= GlobalState.getInstance().getDx() * delta / 1000.;
        if (x <= minX) x = maxX;
        else if (x >= maxX) x = minX;

        float screenHeight = GlobalState.getInstance().getScreenHeight();
        float scaleRate = (screenHeight/2)/y;
        Matrix matrix = new Matrix();
        matrix.setScale(scaleRate, scaleRate);

        if (y > screenHeight/2){
            scaledBitmap = Bitmap.createBitmap(originalBitmap, 0, 0,
                originalBitmap.getWidth(), originalBitmap.getHeight(),
                matrix, true);
        } else {
            scaledBitmap = originalBitmap;
        }
    }

    public boolean isCollided() {
        return collided;
    }

    public void setCollided(boolean collided) {
        this.collided = collided;
    }
}
