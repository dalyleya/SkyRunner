package com.skyrunner.newmap;

import android.graphics.Bitmap;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 28.02.13
 * Time: 1:25
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseMapItem{

    protected Point point;

    protected BaseMapItem(Point point) {
        this.point = point;
    }


    public Point getPoint() {
        return point;
    }

    public abstract Bitmap getBitmap();
}
