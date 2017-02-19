import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Paul on 2017-02-19.
 */
class PointSETTest {

    PointSET pointSET;
    @BeforeEach
    void setUp() {
        pointSET= new PointSET();
    }

    @Test
    void isEmpty() {
        assertEquals(true,pointSET.isEmpty());
        pointSET.insert(new Point2D(0.1,0.2));
        assertEquals(false, pointSET.isEmpty());
    }

    @Test
    void size() {
        pointSET.insert(new Point2D(0.1,0.2));
        assertEquals(1,pointSET.size());
    }


    @Test
    void insertAndContains() {
        Point2D somePoint = new Point2D(0.1,0.1);
        Point2D someOtherPoint = new Point2D(0.2,0.2);
        pointSET.insert(somePoint);
        assertEquals(true,pointSET.contains(somePoint));
        assertEquals(false,pointSET.contains(someOtherPoint));
    }

    @Test
    void range() {
        Stack<Point2D> stack;
        RectHV rectHV = new RectHV(0.2,0.2,0.5,0.5);
        pointSET.insert(new Point2D(0.1,0.2));
        pointSET.insert(new Point2D(0.2,0.5));
        pointSET.insert(new Point2D(0.4,0.6));
        pointSET.insert(new Point2D(0.2,0.7));
        stack = (Stack<Point2D>) pointSET.range(rectHV);
        assertEquals(true, stack.contains(new Point2D(0.2,0.5)));
        assertEquals(false, stack.contains(new Point2D(0.1,0.2)));
        assertEquals(false, stack.contains(new Point2D(0.4,0.6)));
        assertEquals(false, stack.contains(new Point2D(0.2,0.7)));
    }

    @Test
    void nearest() {
        pointSET.insert(new Point2D(0.1,0.2));
        pointSET.insert(new Point2D(0.2,0.5));
        pointSET.insert(new Point2D(0.4,0.6));
        pointSET.insert(new Point2D(0.2,0.7));
        assertEquals(new Point2D(0.2,0.5), pointSET.nearest(new Point2D(0.2,0.45)));
        assertNotEquals(new Point2D(0.1,0.2), pointSET.nearest(new Point2D(0.2,0.45)));
    }

}