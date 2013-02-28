package com.skyrunner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.example.R;

/**
 * Created with IntelliJ IDEA.
 * User: trid
 * Date: 23.02.13
 * Time: 21:46
 * To change this template use File | Settings | File Templates.
 */
public class PlayerCharacter {
    private Bitmap bitmap;
    private float x, y;

    private float angle;

    public PlayerCharacter(Bitmap bitmap, float x, float y) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
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

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
