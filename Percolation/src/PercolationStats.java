import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import static java.lang.StrictMath.sqrt;

/**
 * Created by Paul on 2017-02-09.
 */
public class PercolationStats {

    private double results[];
    private final int numberOfTrials;
    private final int gridSize;

    public PercolationStats(int n, int trials) {
        numberOfTrials = trials;
        gridSize = n;
        results = new double[numberOfTrials];

        for(int i = 1; i <= numberOfTrials; i++){
            int openSites = 0;
            Percolation percolation = new Percolation(gridSize);
            while(!percolation.percolates()){
                int row = StdRandom.uniform(1,gridSize+1);
                int col = StdRandom.uniform(1,gridSize+1);
                if(!percolation.isOpen(row,col)){
                    openSites++;
                    percolation.open(row,col);
                }
            }

            double answer = (double)openSites/(double)(gridSize*gridSize);
            results[i-1] = answer;
        }
    }   // perform trials independent experiments on an n-by-n grid
    public double mean(){
        return StdStats.mean(results);
    }        // sample mean of percolation threshold

    public double stddev(){
        return StdStats.stddev(results);
    }        // sample standard deviation of percolation threshold

    public double confidenceLo(){
        return mean() - 1.96*stddev()/sqrt(numberOfTrials);
    }        // low  endpoint of 95% confidence interval

    public double confidenceHi(){
        return mean() + 1.96*stddev()/sqrt(numberOfTrials);
    }       // high endpoint of 95% confidence interval

    public static void main(String[] args){
        System.out.println("Enter grid size");
        int n =   StdIn.readInt();
        System.out.println("Enter number of trials");
        int trials = StdIn.readInt();
        PercolationStats stats = new PercolationStats(n,trials);
        System.out.println("Mean for " + stats.numberOfTrials + " trials is: " + stats.mean());
        System.out.println("Standard deviation for " + stats.numberOfTrials + " trials is: "+ stats.stddev());
        System.out.println("95% Confidence interval for " + stats.numberOfTrials + " trials is: " + stats.confidenceLo() + " , " + stats.confidenceHi());
    }        // test client (described below)

}