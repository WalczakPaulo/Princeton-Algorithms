import java.util.Arrays;
/**
 * Created by Paul on 2017-02-11.
 */
public class BruteCollinearPoints {
    Point[] points;
    LineSegment[] lineSegments;
    int numberOfSegments;

    public BruteCollinearPoints(Point[] points) {
        if(points == null)
            throw new NullPointerException();
        for (int i = 0; i < points.length; i++)
            this.points[i] = points[i];

        Arrays.sort(points);
        numberOfSegments = 0;
    }   // finds all line segments containing 4 points

    public int numberOfSegments() {
        return numberOfSegments;
    }// the number of line segments

    public LineSegment[] segments() {
        for (int i = 0; i < points.length - 3; i++)
            for (int j = i + 1; j < points.length - 2; j++)
                for (int k = j + 1; k < points.length - 1; k++)
                    for (int l = k + 1; l < points.length; l++) {
                        if(areCollinear(points[i], points[j], points[k], points[l])){
                            lineSegments[numberOfSegments++] = new LineSegment(points[i], points[l]);
                        }
                    }
        return lineSegments;
    }                // the line segments

    boolean areCollinear(Point p1, Point p2, Point p3, Point p4){
        if (p1 == null || p2 == null || p3 == null || p4 == null)
            throw new NullPointerException();
        double slope1 = p1.compareTo(p2);
        double slope2 = p1.compareTo(p3);
        double slope3 = p1.compareTo(p4);

        if(slope1 == slope2 && slope1 == slope3)
            return true;
        else
            return false;
    }
}
