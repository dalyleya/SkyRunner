package map;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 27.02.13
 * Time: 22:37
 * To change this template use File | Settings | File Templates.
 */
public abstract class RoundMapItem implements MapItem{

    protected ItemSize size;
    protected Point point;

    protected RoundMapItem(Point point, ItemSize size) {
        this.point = point;
        this.size = size;
    }

    @Override
    public Point getPoint() {
        return point;
    }

    @Override
    public ItemSize getSize() {
        return size;
    }

    @Override
    public boolean isIntersect(MapItem item) throws ItemNotRoundException {
        if (! (item instanceof RoundMapItem))  throw new ItemNotRoundException("Exception on RoundMapItem");
        else if (point.getDistance( item.getPoint()) < (size.getDiameter() + item.getSize().getDiameter()))
        return false;
        return true;
    }

}
