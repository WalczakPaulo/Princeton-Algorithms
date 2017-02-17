import sun.awt.image.ImageWatched;

import java.util.LinkedList;
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
        if(row == board.length - 1 && col == board.length - 1)
            return 0;
        else
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
    public boolean equals(Object y) {
        if (y==this)
            return true;
        else if (y==null || !(y instanceof Board) || ((Board)y).board.length != board.length)
            return false;
        else
            for (int row = 0; row < board.length; row++)
                for (int col = 0; col < board.length; col++)
                    if (((Board) y).board[row][col] != board[row][col]) return false;

        return true;
    }    // does this board position equal y

    public int[][] swapTiles(int oldRow, int oldCol, int newRow, int newCol){
        int[][] copyBoard = new int[this.board.length][this.board.length];
        for(int i = 0; i < this.board.length; i++)
            for(int j = 0; j < this.board.length; j++)
                copyBoard[i][j] = this.board[i][j];
        copyBoard[oldRow][oldCol] = this.board[newRow][newCol];
        copyBoard[newRow][newCol] = this.board[oldRow][oldCol];
        return copyBoard;
    }

    public int[] locateSpace() {
        for (int row = 0; row < board.length; row++)
            for (int col = 0; col < board.length; col++)
                if (board[row][col] == 0) {
                    int[] position = new int[2];
                    position[0] = row;
                    position[1] = col;
                    return position;
                }
        throw new UnsupportedOperationException();
    }

    public Iterable<Board> neighbors(){
        LinkedList <Board> neighbours = new LinkedList<Board>();
        int[] spaceLocation = locateSpace();
        int spaceRow = spaceLocation[0];
        int spaceCol = spaceLocation[1];
        if(spaceRow > 0 )
            neighbours.add(new Board(swapTiles(spaceRow, spaceCol, spaceRow - 1, spaceCol)));
        if(spaceRow < board.length - 1)
            neighbours.add(new Board(swapTiles(spaceRow, spaceCol, spaceRow + 1, spaceCol)));
        if(spaceCol > 0 )
            neighbours.add(new Board(swapTiles(spaceRow, spaceCol, spaceRow, spaceCol - 1)));
        if(spaceCol < board.length - 1)
            neighbours.add(new Board(swapTiles(spaceRow, spaceCol, spaceRow, spaceCol + 1)));

        return neighbours;
    }  // return an Iterable of all neighboring board positions
    public String toString() {
        StringBuilder info = new StringBuilder();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++)
                info.append(String.format("%2d ", board[row][col]));
            info.append("\n");
        }

        return info.toString();
    }           // return a string representation of the board

    public Board makeTwin(){
        for (int row = 0; row < board.length; row++)
            for (int col = 0; col < board.length - 1; col++)
                if(this.board[row][col] != 0 && this.board[row][col+1] != 0 )
                    return new Board(swapTiles(row ,col ,row ,col + 1));
        throw new UnsupportedOperationException();

    }

    public boolean isFinalBoard(){
        for (int row = 0; row < board.length; row++)
            for (int col = 0; col < board.length; col++)
                if(this.board[row][col] != rightTile(row,col))
                    return false;
        return true;
    }

}