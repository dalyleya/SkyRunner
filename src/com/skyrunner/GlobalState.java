package com.skyrunner;

import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: trid
 * Date: 24.02.13
 * Time: 1:56
 * To change this template use File | Settings | File Templates.
 */
public class GlobalState {
    private static GlobalState ourInstance = new GlobalState();

    private float dx = 0f;
    private float screenHeight = 0f;
    private float screenWidth = 0f;

    public static GlobalState getInstance() {
        return ourInstance;
    }

    private GlobalState() {
    }

    public float getDx() {
        return dx;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    //There MAY be a better way. but i'm just too lazy
    public void setScreenHeight(float screenHeight) {
        this.screenHeight = screenHeight;
    }

    public void setScreenWidth(float screenWidth) {
        this.screenWidth = screenWidth;
    }

    public float getScreenHeight() {
        return screenHeight;
    }

    public float getScreenWidth() {
        return screenWidth;
    }
}
