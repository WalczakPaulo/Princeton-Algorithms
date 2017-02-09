import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
/**
 * Created by Paul on 2017-02-09.
 */
public class Percolation {

    private int gridSize;
    private boolean [] openSites;
    private WeightedQuickUnionUF perlocation;
    final int topIndex;
    final int bottomIndex;


    public Percolation(int n){
        if (n < 1)
            throw new IllegalArgumentException("Wrong input argument");
        gridSize = n;
        openSites = new boolean [gridSize*gridSize + 2];
        perlocation = new WeightedQuickUnionUF(gridSize*gridSize+2);
        topIndex = 0;
        bottomIndex = gridSize*gridSize + 1;
        openSites[topIndex] = true;
        openSites[bottomIndex] = true;

        for(int i = 1; i <= gridSize; i++){
            int temporaryIndex = 1;
            int topRowIndex = calculateIndex(temporaryIndex, i);
            perlocation.union(topIndex, topRowIndex);

            temporaryIndex = gridSize;
            int bottomRowIndex = calculateIndex(temporaryIndex, i );
            perlocation.union(bottomIndex, bottomRowIndex);
        }

    }

    public int calculateIndex(int x, int y){
        checkIfBoundsRight(x,y);
        return (x-1)*gridSize + y;
    }
    public void checkIfBoundsRight(int x, int y) {
        if (x < 1 || x > gridSize)
            throw new IndexOutOfBoundsException("Wrong 'x' - row specified");
        if (y < 1 || y > gridSize)
            throw new IndexOutOfBoundsException("Wrong 'y' - column specified");
    }
    public void open(int row, int col)  {
        int siteIndex = calculateIndex(row, col);
        if (!isOpen(row,col))
        {
            openSites[siteIndex] = true;

            if(row > 1 && isOpen(row-1,col)){
                int upperSite = calculateIndex(row - 1, col);
                perlocation.union(siteIndex, upperSite);
            }
            if(row < gridSize && isOpen(row+1,col)){
                int downSite = calculateIndex(row + 1, col);
                perlocation.union(siteIndex, downSite);
            }
            if(col > 1 && isOpen(row,col - 1)){
                int leftSite = calculateIndex(row, col - 1);
                perlocation.union(siteIndex, leftSite);
            }
            if(col < gridSize && isOpen(row, col + 1)){
                int rightSite = calculateIndex(row, col + 1);
                perlocation.union(siteIndex, rightSite);
            }
        }
    }
    public boolean isOpen(int row, int col){
        int sideIndex = calculateIndex(row,col);
        return openSites[sideIndex];
    }  // is site (row, col) open?

    public boolean percolates() {
        if(gridSize == 1)
            return isOpen(1,1);
        else
            return perlocation.connected(topIndex, bottomIndex);
    }             // does the system percolate?

    public static void main(String[] args){
        /**
         * tests purposes
         */
//            Percolation percolation = new Percolation(1);
//            System.out.println(percolation.percolates());
//            percolation.open(1,1);
//            System.out.println(percolation.percolates());
//            Percolation twoByTwo = new Percolation(2);
//            System.out.println(twoByTwo.percolates());
//            twoByTwo.open(1,1);
//            System.out.println(twoByTwo.percolates());
//            twoByTwo.open(2,1);
//            System.out.println(twoByTwo.percolates());
//            twoByTwo.open(2,2);
//            System.out.println(twoByTwo.percolates());
//            twoByTwo.open(1,2);
//            System.out.println(twoByTwo.percolates());

    }
}
