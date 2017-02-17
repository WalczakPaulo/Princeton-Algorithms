import edu.princeton.cs.algs4.StdIn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Paul on 2017-02-17.
 */
class SolverTest {
    Solver solver;
    int[][] tiles1;
    int[][] tiles2;
    @BeforeEach
    void setUp() {
        tiles1 = new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        tiles2 = new int[][]{{1, 2, 3}, {4, 5, 6}, {8, 7, 0}};
    }

    @Test
    void isSolvable() {
        Board initial = new Board(tiles1);
        solver = new Solver(initial);
        assertEquals(true,solver.isSolvable());
        initial = new Board(tiles2);
        solver = new Solver(initial);
        assertEquals(false,solver.isSolvable());
    }

    @Test
    void checkNumOfMoves(){
        Board initial = new Board(tiles1);
        solver = new Solver(initial);
        assertEquals(4,solver.moves());
        initial = new Board(tiles2);
        solver = new Solver(initial);
        assertEquals(-1,solver.moves());
    }

}