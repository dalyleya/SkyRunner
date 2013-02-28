package com.skyrunner.newmap;

import android.graphics.Bitmap;
import map.ItemSize;
import map.Point;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 27.02.13
 * Time: 22:35
 * To change this template use File | Settings | File Templates.
 */
public class Target extends BaseMapItem {
    private Bitmap bitmap;

    public Target(Bitmap bitmap, com.skyrunner.newmap.Point point) {
        super(point);
        this.bitmap = bitmap;
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}
