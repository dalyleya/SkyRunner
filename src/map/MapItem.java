package map;

import android.graphics.Bitmap;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 27.02.13
 * Time: 21:47
 * To change this template use File | Settings | File Templates.
 */
public interface MapItem {
    Point getPoint();
    ItemSize getSize();
    boolean isIntersect(MapItem item) throws ItemNotRoundException;
    Bitmap getBitmap();
}
