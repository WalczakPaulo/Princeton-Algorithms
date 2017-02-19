import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import java.util.Stack;

/**
 * Created by Paul on 2017-02-19.
 */
public class PointSET {
    private SET<Point2D> points;

    public PointSET() {
        points = new SET<Point2D>();
    }                             // construct an empty set of points

    public boolean isEmpty() {
        return points.size() == 0;
    }                      // is the set empty?

    public int size() {
        return points.size();
    }                        // number of points in the set

    public void insert(Point2D p) {
        if(p == null)
            throw new NullPointerException();
        points.add(p);
    }              // add the point to the set (if it is not already in the set)

    public  boolean contains(Point2D p) {
        if(p == null)
            throw new NullPointerException();
        return points.contains(p);
    }           // does the set contain point p?

    public void draw() {
        for (Point2D point: points)
            point.draw();
    }                         // draw all points to standard draw
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> stack = new Stack();
        for(Point2D point: points)
          if(rect.contains(point))
              stack.push(point);
        return stack;
    } // all points that are inside the rectangle
    public Point2D nearest(Point2D p) {
        if(isEmpty())
            throw new NullPointerException();
        double distance = Double.MAX_VALUE;
        Point2D nearestPoint = points.max();
        distance = p.distanceTo(nearestPoint);
        for(Point2D point: points) {
            if(p.distanceTo(point) < distance) {
                nearestPoint = point;
                distance = p.distanceTo(point);
            }
        }
        return nearestPoint;
    }            // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {

    }                  // unit testing of the methods (optional)
}
