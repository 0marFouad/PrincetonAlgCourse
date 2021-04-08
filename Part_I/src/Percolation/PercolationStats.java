package Percolation;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;



public class PercolationStats {
    private final int trials;
    private final double[] means;
    static final double CONFIDENCE_95 = 1.96;

    private double startTrial(int n) {
        Percolation perc = new Percolation(n);
        boolean map[] = new boolean[n*n];
        int num = 0;
        while(!perc.percolates()) {
            int random = StdRandom.uniform(n*n);
            if(!map[random]) {
                perc.open(random/n + 1, random%n + 1);
                num++;
                map[random] = true;
            }
        }
        return ((double)num/(double)(n*n));
    }

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if(trials <= 0 || n <= 0) {
            throw new IllegalArgumentException();
        }
        this.trials = trials;
        means = new double[trials];
        for(int i=0;i<trials;i++) {
            means[i] = startTrial(n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(means);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(means);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double shift = (CONFIDENCE_95)*this.stddev()/(Math.sqrt(trials));
        return (this.mean() - shift);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double shift = (CONFIDENCE_95)*this.stddev()/(Math.sqrt(trials));
        return (this.mean() + shift);
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean = " + ps.mean());
        System.out.println("stddev = "+ ps.stddev());
        System.out.println("95% confidence interval = [" +ps.confidenceLo()+", " + ps.confidenceHi()+"]");
    }

}