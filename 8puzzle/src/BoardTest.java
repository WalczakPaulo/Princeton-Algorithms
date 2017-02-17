import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Paul on 2017-02-17.
 */
class BoardTest {

    Board board;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        int[][] tiles = new int[][]{{8,7,6},{5,4,3},{2,1,0}};
        board = new Board(tiles);
    }

    @org.junit.jupiter.api.Test
    void testTiles() {
        assertEquals(3, board.rightTile(0,2));
        assertEquals(5, board.rightTile(1,1));
    }

    @org.junit.jupiter.api.Test
    void testTilePosition() {
        int[] position = board.tilePosition(8);
        assertEquals(2, position[0] );
        assertEquals(1,position[1]);
    }

    @org.junit.jupiter.api.Test
    void hammingTest() {
        assertEquals(8, board.hamming());
    }

    @org.junit.jupiter.api.Test
    void manhattanTest() {
        assertEquals(16,board.manhattan());
    }

}