package map;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 27.02.13
 * Time: 21:49
 * To change this template use File | Settings | File Templates.
 */
public class Point {

    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDistance(Point p1){
        return Math.sqrt( Math.pow(x - p1.x, 2) - Math.pow( y - p1.y, 2) );
    }
}
