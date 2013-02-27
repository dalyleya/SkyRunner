package map;

import android.graphics.Bitmap;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 27.02.13
 * Time: 23:09
 * To change this template use File | Settings | File Templates.
 */
public class Tree extends RoundMapItem {

    private Bitmap bitmap;

    public Tree(Bitmap bitmap, Point point, ItemSize size) {
        super(point, size);
        this.bitmap = bitmap;
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
