package map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 27.02.13
 * Time: 22:04
 * To change this template use File | Settings | File Templates.
 */
public class FieldMap {

    private List<MapItem> items;
    private Point lefttopCorner;
    private double high;
    private double width;

    public FieldMap(Point lefttopCorner, double high, double width) {
        this.items = new ArrayList<MapItem>();
        this.lefttopCorner = lefttopCorner;
        this.high = high;
        this.width = width;
    }

    public List<MapItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public boolean addItem(MapItem newItem){
        if (!canAdd(newItem)) return false;
        items.add(newItem);
        return true;
    }

    private boolean canAdd(MapItem newItem) {
        if (!isInside(newItem)) return false;
          for (MapItem item : items)   {
              try {
                  if (item.isIntersect(newItem)) return false;
              } catch (ItemNotRoundException e) {
                  e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
              }
          }
        return true;
    }

    private boolean isInside(MapItem newItem) {
        Point point = newItem.getPoint();
        ItemSize size = newItem.getSize();
        if ( ((point.getX() +  size.getDiameter()) > (lefttopCorner.getX() + width) ) ||
             ((point.getX() +  size.getDiameter()) > (lefttopCorner.getY() + high) )   ||
             ((point.getX() -  size.getDiameter()) < (lefttopCorner.getX()) )   ||
             ((point.getY() -  size.getDiameter()) < (lefttopCorner.getX()) ) )
        {
          return false;
        }
        return true;
    }

}
