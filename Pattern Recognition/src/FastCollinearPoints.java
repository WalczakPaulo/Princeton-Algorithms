import java.util.*;

/**
 * Created by Paul on 2017-02-11.
 */
public class FastCollinearPoints {
    private LineSegment[] lineSegments;
    private Point[] points;
    private int numberOfSegments;
    public FastCollinearPoints(Point[] points) {
        if(points == null)
            throw new NullPointerException();
        for(int i = 0 ; i < points.length; i++){
            if(points[i] == null)
                throw new NullPointerException();
            this.points[i] = points[i];
        numberOfSegments = 0;
        }
    }     // finds all line segments containing 4 or more points
    public int numberOfSegments() {
        return numberOfSegments;
    }       // the number of line segments
    public LineSegment[] segments() {
        double previousSlope = 0;
        for(int i = 0 ; i < points.length; i++){
            sort(points,points[i].BY_SLOPE);
            for( int j = 0 ; j < points.length; j++){
                ArrayList<Point> collinearPoints = new ArrayList<>(points.length);
                if(i == j)
                    continue;
                if(collinearPoints.isEmpty()) {
                    previousSlope = points[i].slopeTo(points[j]);
                    collinearPoints.add(points[j]);
                }
                else if(points[i].slopeTo(points[j]) == previousSlope){
                    previousSlope = points[i].slopeTo(points[j]);
                    collinearPoints.add(points[j]);
                }
                else if(collinearPoints.size() >= 3){
                    Collections.sort(collinearPoints);
                    lineSegments[numberOfSegments++] = new LineSegment(collinearPoints.get(0),collinearPoints.get(collinearPoints.size()-1));
                    collinearPoints.clear();
                }

            }
        }
        return lineSegments;
    }// the line segments



    /***********************************************************************
     *  Bottom-Up merge sorting functions
     ***********************************************************************/

    // stably merge a[lo..m] with a[m+1..hi] using aux[lo..hi]
    private static void merge(Point[] a, Point[] aux, int lo, int m, int hi, Comparator<Point> comparator) {
        // copy to aux[]
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }
        // merge back to a[]
        int i = lo, j = m+1;
        for (int k = lo; k <= hi; k++) {
            if      (i > m)                a[k] = aux[j++];
            else if (j > hi)               a[k] = aux[i++];
            else if (less(comparator, aux[j], aux[i])) a[k] = aux[j++];
            else                           a[k] = aux[i++];
        }
    }

    // bottom-up mergesort
    public static void sort(Point[] a, Comparator<Point> comparator) {
        int N = a.length;
        Point[] aux = new Point[N];
        for (int n = 1; n < N; n = n+n) {
            for (int i = 0; i < N-n; i += n+n) {
                int lo = i;
                int m  = i+n-1;
                int hi = Math.min(i+n+n-1, N-1);
                merge(a, aux, lo, m, hi, comparator);
            }
        }
    }

    /***********************************************************************
     *  Helper sorting functions
     ***********************************************************************/

    // is v < w ?
    private static boolean less(Comparator<Point> comparator, Point v, Point w) {
        return comparator.compare(v, w) < 0;
    }

    public static void main(String[] args){
        
    }

}
