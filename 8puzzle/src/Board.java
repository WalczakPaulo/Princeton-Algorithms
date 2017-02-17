/**
 * Created by Paul on 2017-02-13.
 */
public class Board {
    private int[][] board;

    public Board(int[][] tiles) {
        this.board = new int[tiles.length][tiles.length];
        for(int i = 0; i < tiles.length; i++)
            for(int j = 0; j < tiles.length; j++)
                this.board[i][j] = tiles[i][j];
    }        // construct a board from an N-by-N array of tiles

    public int rightTile(int row, int col) {
        return row*board.length + col + 1;
    }

    public int[] tilePosition(int value){
        int position[] = new int[2];
        position[0] = value / (board.length + 1);
        position[1] = value - position[0]*board.length - 1;
        return position;
    }

    public int hamming() {
        int countWrongPos = 0;
        for(int row = 0; row < board.length; row++)
            for(int col = 0 ; col < board.length; col++)
                if(board[row][col] != 0 && board[row][col] != rightTile(row,col))
                    countWrongPos++;
        return countWrongPos;
    }               // return number of blocks out of place
    public int manhattan() {
        int[] position;
        int countDistances = 0;
        for(int row = 0; row < board.length; row++)
            for(int col = 0 ; col < board.length; col++)
                if(board[row][col] != 0 ) {
                    position = tilePosition(board[row][col]);
                    countDistances += (Math.abs(row - position[0]) + Math.abs(col - position[1]));
                }
        return countDistances;
    }            // return sum of Manhattan distances between blocks and goal
    //public boolean equals(Object y);    // does this board position equal y
    //public Iterable<Board> neighbors(); // return an Iterable of all neighboring board positions
    //public String toString();           // return a string representation of the board
}