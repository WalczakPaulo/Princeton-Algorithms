import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdIn;
import java.util.Stack;

/**
 * Created by Paul on 2017-02-13.
 */
public class Solver {

    private class Move implements Comparable<Move> {
        private Board board;
        private Move previousMove;
        private int numberOfMoves;

        public Move(Board board){
            this.board = board;
            previousMove = null;
            numberOfMoves = 0;
        }

        public Move(Board board, Move previousMove, int numberOfMoves){
            this.board = board;
            this.previousMove = previousMove;
            this.numberOfMoves = numberOfMoves + 1;
        }

        @Override
        public int compareTo(Move someMove){
            return (this.board.hamming() - someMove.board.hamming() + this.numberOfMoves - someMove.numberOfMoves);
        }
    }

    private Move lastMove;

    public Solver(Board initial){
        MinPQ <Move> moves = new MinPQ<>();
        MinPQ <Move> twinMoves = new MinPQ<>();

        moves.insert(new Move(initial));
        twinMoves.insert(new Move(initial.makeTwin()));

        while(true){
            lastMove = nextMove(moves);
            if(lastMove != null) {
                return;
            }
            else if (nextMove(twinMoves) != null) {
                return;
            }

        }
    }       // find a solution to the initial board
    public boolean isSolvable() {
        if (lastMove != null)
            return true;
        else
            return false;
    }         // is the initial board solvable?

    public Move nextMove(MinPQ<Move> moves){
        if(moves.isEmpty())
            return null;
        Move bestMove = moves.delMin();
        if (bestMove.board.isFinalBoard())
            return bestMove;
        for (Board neighbor : bestMove.board.neighbors()) {
            if (bestMove.previousMove == null || !neighbor.equals(bestMove.previousMove.board)) {
                moves.insert(new Move(neighbor, bestMove, bestMove.numberOfMoves));
            }
        }
        return null;
    }

    public int moves() {
        if (lastMove != null)
            return lastMove.numberOfMoves;
        else
            return -1;
    }                 // return min number of moves to solve initial board; -1 if no solution
    public Iterable<Board> solution() {
        if(isSolvable() == true) {
            Move iterMove = lastMove;
            Stack <Board> solution = new Stack(); // use of stack to reverse iterations
            while(iterMove != null){
                solution.push(iterMove.board);
                iterMove = iterMove.previousMove;
            }
            return solution;
        }
        else
            throw new UnsupportedOperationException();
    }   // return an Iterable of board positions in solution

    public static void main(String[] args) {
        System.out.println("Enter grid length");
        int N = StdIn.readInt();
        int[][] tiles = new int[N][N];
        System.out.println("Fill grid");
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                tiles[i][j] = StdIn.readInt();
        System.out.println("Solving...");
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);
        if (!solver.isSolvable()) {
            System.out.println("No solution possible");
        }
        else {
            Stack <Board> solution = (Stack) solver.solution();
            while(!solution.isEmpty())
                System.out.println(solution.pop());
            System.out.println("Minimum number of moves = " + solver.moves());
        }
    }
}
