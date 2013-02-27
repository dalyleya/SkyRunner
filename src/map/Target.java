package map;

import android.graphics.Bitmap;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 27.02.13
 * Time: 22:35
 * To change this template use File | Settings | File Templates.
 */
public class Target extends RoundMapItem{

    private Bitmap bitmap;

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    public Target(Bitmap bitmap, Point point, ItemSize size) {
        super(point, size);
        this.bitmap = bitmap;
    }
}
