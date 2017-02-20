import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Stack;

/**
 * Created by Paul on 2017-02-20.
 */

public class KdTree {

    private static class Node {
        private Node left;
        private Node right;
        private Point2D point;
        private RectHV rect;

        public Node(Point2D point, RectHV rect) {
            if (rect == null)
                rect = new RectHV(0, 0, 1, 1);
            this.rect = rect;
            this.point = point;
        }
    }

    private enum Orientation {
        Vertical, Horizontal;

        public Orientation change() {
            if (this.equals(Orientation.Horizontal))
                return Orientation.Vertical;
            return Orientation.Horizontal;
        }
    }

    private Node root;
    private int  size;

    public KdTree() {
        size = 0;
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    private Node insertHorizontal(Node x, Point2D p, RectHV rect) {
        if (x == null) {
            size++;
            return new Node(p, rect);
        }

        if (x.point.equals(p))
            return x; // no need to add anything new

        RectHV r;
        int cmp = Point2D.Y_ORDER.compare(x.point, p);
        if (cmp > 0) {
            if (x.left == null)
                r = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), x.point.y());
            else
                r = x.left.rect;
            x.left = insertVertical(x.left, p, r);
        } else {
            if (x.right == null)
                r = new RectHV(rect.xmin(), x.point.y(), rect.xmax(), rect.ymax());
            else
                r = x.right.rect;
            x.right = insertVertical(x.right, p, r);
        }

        return x;
    }

    private Node insertVertical(Node x, Point2D p, RectHV rect) {
        if (x == null) {
            size++;
            return new Node(p, rect);
        }
        if (x.point.equals(p))
            return x;

        RectHV r;
        int cmp = Point2D.X_ORDER.compare(x.point, p);
        if (cmp > 0) {
            if (x.left == null)
                r = new RectHV(rect.xmin(), rect.ymin(), x.point.x(), rect.ymax());
            else
                r = x.left.rect;
            x.left = insertHorizontal(x.left, p, r);
        } else {
            if (x.right == null)
                r = new RectHV(x.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
            else
                r = x.right.rect;
            x.right = insertHorizontal(x.right, p, r);
        }

        return x;
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (isEmpty())
            root = insertVertical(root, p, null);
        else
            root = insertVertical(root, p, root.rect);
    }

    private boolean contains(Node x, Point2D p, Orientation orientation) {
        if (x == null)
            return false;
        if (x.point.equals(p))
            return true;
        int cmp = 0;
        if (orientation == Orientation.Vertical)
            cmp = Point2D.X_ORDER.compare(x.point, p);
        else
            cmp = Point2D.Y_ORDER.compare(x.point, p);
        if (cmp > 0)
            return contains(x.left, p, orientation.change());
        else
            return contains(x.right, p, orientation.change());
    }

    public boolean contains(Point2D p) {
        return contains(root, p, Orientation.Vertical);
    }

    private void draw(Node x, Orientation orientation) {
        if (x.left != null)
            draw(x.left, orientation.change());
        if (x.right != null)
            draw(x.right, orientation.change());

        // draw the point first
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        StdDraw.point(x.point.x(), x.point.y());

        // draw the line
        double xmin, ymin, xmax, ymax;
        if (orientation == Orientation.Vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            ymin = x.rect.ymin();
            ymax = x.rect.ymax();
            xmin = x.point.x();
            xmax = x.point.x();
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            ymin = x.point.y();
            ymax = x.point.y();
            xmin = x.rect.xmin();
            xmax = x.rect.xmax();
        }
        StdDraw.setPenRadius();
        StdDraw.line(xmin, ymin, xmax, ymax);
    }

    public void draw() {
        StdDraw.rectangle(0.5, 0.5, 0.5, 0.5);
        if (isEmpty())
            return;
        draw(root, Orientation.Vertical);
    }

    private void range(Node x, RectHV rect, Stack<Point2D> q) {
        if (x == null)
            return;
        if (rect.contains(x.point))
            q.push(x.point);
        if (x.left != null && rect.intersects(x.left.rect))
            range(x.left, rect, q);
        if (x.right != null && rect.intersects(x.right.rect))
            range(x.right, rect, q);
    }

    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> q = new Stack<Point2D>();
        range(root, rect, q);
        return q;
    }

    private Point2D nearest(Node x, Point2D p, Point2D mp, Orientation orientation) {
        Point2D min = mp;

        if (x == null)
            return min;
        if (p.distanceSquaredTo(x.point) < p.distanceSquaredTo(min))
            min = x.point;

        // choose the side that contains the query point first
        if (orientation == Orientation.Vertical) {
            if (x.point.x() < p.x()) {
                min = nearest(x.right, p, min, orientation.change());
                if (x.left != null
                        && (min.distanceSquaredTo(p)
                        > x.left.rect.distanceSquaredTo(p)))
                    min = nearest(x.left, p, min, orientation.change());
            } else {
                min = nearest(x.left, p, min, orientation.change());
                if (x.right != null
                        && (min.distanceSquaredTo(p)
                        > x.right.rect.distanceSquaredTo(p)))
                    min = nearest(x.right, p, min, orientation.change());
            }
        } else {
            if (x.point.y() < p.y()) {
                min = nearest(x.right, p, min, orientation.change());
                if (x.left != null
                        && (min.distanceSquaredTo(p)
                        > x.left.rect.distanceSquaredTo(p)))
                    min = nearest(x.left, p, min, orientation.change());
            } else {
                min = nearest(x.left, p, min, orientation.change());
                if (x.right != null
                        && (min.distanceSquaredTo(p)
                        > x.right.rect.distanceSquaredTo(p)))
                    min = nearest(x.right, p, min, orientation.change());
            }
        }
        return min;
    }

    // a nearest neighbor in the set to p: null if set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()) return null;
        return nearest(root, p, root.point, Orientation.Vertical);
    }
}