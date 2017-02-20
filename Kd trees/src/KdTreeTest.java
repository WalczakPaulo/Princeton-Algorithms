import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Paul on 2017-02-20.
 */
class KdTreeTest {
    KdTree kdTree;
    @BeforeEach
    void setUp() {
        kdTree = new KdTree();
    }

    @Test
    void isEmpty() {
        assertEquals(true, kdTree.isEmpty());
        kdTree.insert(new Point2D(0.1,0.1));
        assertEquals(false,kdTree.isEmpty());
    }

    @Test
    void size() {
        assertEquals(0,kdTree.size());
        kdTree.insert(new Point2D(0.1,0.1));
        assertEquals(1,kdTree.size());
    }


    @Test
    void contains() {
        kdTree.insert(new Point2D(0.1,0.1));
        assertEquals(true,kdTree.contains(new Point2D(0.1,0.1)));
        assertEquals(false,kdTree.contains(new Point2D(0.2,0.2)));
    }

    @Test
    void range() {
        Stack<Point2D> stack;
        RectHV rectHV = new RectHV(0.2,0.2,0.5,0.5);
        kdTree.insert(new Point2D(0.1,0.1));
        kdTree.insert(new Point2D(0.2,0.2));
        kdTree.insert(new Point2D(0.6,0.6));
        stack = (Stack<Point2D>) kdTree.range(rectHV);
        assertEquals(true,stack.contains(new Point2D(0.2,0.2)));
        assertEquals(false,stack.contains(new Point2D(0.1,0.1)));
        assertEquals(false,stack.contains(new Point2D(0.5,0.5)));
    }

    @Test
    void nearest() {
        kdTree.insert(new Point2D(0.1,0.1));
        kdTree.insert(new Point2D(0.2,0.2));
        kdTree.insert(new Point2D(0.6,0.6));
        kdTree.insert(new Point2D(0.8,0.8));
        assertEquals(new Point2D(0.1,0.1), kdTree.nearest(new Point2D(0.15,0.15)));
    }

}