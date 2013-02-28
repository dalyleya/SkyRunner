package com.skyrunner.newmap;

import android.graphics.Bitmap;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 27.02.13
 * Time: 23:11
 * To change this template use File | Settings | File Templates.
 */
public class Lake extends BaseMapItem {
    private Bitmap bitmap;

    public Lake(Bitmap bitmap, Point point) {
        super(point);
        this.bitmap = bitmap;
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}
