package Percolation;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;



public class Percolation {

    private boolean[][] grid;
    private int opened;
    private final int n;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF uf2;
    private final int upperNode;
    private final int lowerNode;
    private final int fullNode;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if(n <= 0) {
            throw new IllegalArgumentException();
        }
        //assume closed = 0;
        grid = new boolean[n][n];
        opened = 0;
        this.n = n;
        uf = new WeightedQuickUnionUF(n*n + 2);
        uf2 = new WeightedQuickUnionUF(n*n + 1);
        upperNode = n*n;
        lowerNode = n*n + 1;
        fullNode = n*n;
    }

    private boolean isValid(int row, int col) {
        if(row <= 0 || row > n || col <= 0 || col > n) {
            return false;
        }
        return true;
    }

    private int getNumber(int row, int col) {
        return ((row-1)*n) + (col-1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if(isOpen(row, col)) {
            return;
        }
        if(!isValid(row, col)) {
            throw new IllegalArgumentException();
        }
        if(row == 1) {
            uf2.union(getNumber(row, col), fullNode);
            uf.union(getNumber(row,col), upperNode);
        }
        if(row == n) {
            uf.union(getNumber(row, col), lowerNode);
        }
        if(isValid(row-1, col) && isOpen(row-1, col)) {
            uf.union(getNumber(row, col), getNumber(row-1, col));
            uf2.union(getNumber(row, col), getNumber(row-1, col));
        }
        if(isValid(row+1, col) && isOpen(row+1, col)) {
            uf.union(getNumber(row, col), getNumber(row+1, col));
            uf2.union(getNumber(row, col), getNumber(row+1, col));
        }
        if(isValid(row, col-1) && isOpen(row, col-1)) {
            uf.union(getNumber(row, col), getNumber(row, col-1));
            uf2.union(getNumber(row, col), getNumber(row, col-1));
        }
        if(isValid(row, col+1) && isOpen(row, col + 1)) {
            uf.union(getNumber(row, col), getNumber(row, col+1));
            uf2.union(getNumber(row, col), getNumber(row, col+1));
        }
        opened++;
        grid[row-1][col-1] = true;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if(!isValid(row, col)) {
            throw new IllegalArgumentException();
        }
        return grid[row-1][col-1] == true;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if(!isValid(row, col)) {
            throw new IllegalArgumentException();
        }
        if(uf2.find(getNumber(row, col)) == uf2.find(fullNode)) {
            return true;
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return opened;
    }

    // does the system percolate?
    public boolean percolates() {
        if(uf.find(lowerNode) == uf.find(upperNode)) {
            return true;
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
