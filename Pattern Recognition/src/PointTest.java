import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Paul on 2017-02-11.
 */
class PointTest {

    Point point1;
    Point point2;
    Point point3;
    Point point4;
    Point point5;

    @org.junit.jupiter.api.BeforeEach

    void setUp() {
        point1 = new Point(1,1);
        point2 = new Point(1,1);
        point3 = new Point(1,2);
        point4 = new Point(2,1);
        point5 = new Point(5,2);
    }

    @org.junit.jupiter.api.Test
    void compareTo() {
        assertEquals(0,point1.compareTo(point2));
        assertEquals(-1,point1.compareTo(point4));
        assertEquals(-1,point1.compareTo(point3));
        assertEquals(1,point5.compareTo(point1));
        assertEquals(1,point4.compareTo(point1));
    }

    @org.junit.jupiter.api.Test
    void slopeTo() {
       assertEquals(0.25, point5.slopeTo(point1));
       assertEquals(0.0,point4.slopeTo(point1));
       assertEquals(Double.POSITIVE_INFINITY, point3.slopeTo(point1));
       assertEquals(Double.NEGATIVE_INFINITY, point2.slopeTo(point1));
    }


}