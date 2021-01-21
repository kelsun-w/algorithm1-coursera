import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[] isOpen;
    private int openCount;
    private final int n;
    private final int topRoot;
    private final int botRoot;
    private final WeightedQuickUnionUF normalQU;
    private final WeightedQuickUnionUF backwashQU;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n must be greater than 0!");

        this.n = n;
        normalQU = new WeightedQuickUnionUF(n * n + 1); // without bottom site
        backwashQU = new WeightedQuickUnionUF(n * n + 2); // with both top & bottom site
        topRoot = 0;
        botRoot = n * n + 1;
        isOpen = new boolean[n * n + 2];
        isOpen[topRoot] = true;
        isOpen[botRoot] = true;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if(isOpen(row,col)) return;

        int currIndex = xyTo1D(row, col);
        isOpen[currIndex] = true;
        openCount++;

        if (row == 1) {
            normalQU.union(currIndex, topRoot);
            backwashQU.union(currIndex, topRoot);
        }
        if (row == n) {
            backwashQU.union(currIndex, botRoot);
        }

        // Connect the index in question with it's open neighbours
        connectNeighbour(row, col, row - 1, col);   // North
        connectNeighbour(row, col, row, col + 1);   // East
        connectNeighbour(row, col, row + 1, col);   // South
        connectNeighbour(row, col, row, col - 1);   // West
    }

    private void connectNeighbour(int rowA, int colA, int rowB, int colB) {
        if (0 < rowB && rowB <= n
                && 0 < colB && colB <= n
                && isOpen(rowB, colB))
        {
            normalQU.union(xyTo1D(rowA, colA), xyTo1D(rowB, colB));
            backwashQU.union(xyTo1D(rowA, colA), xyTo1D(rowB, colB));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return isOpen[xyTo1D(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return normalQU.find(topRoot) == normalQU.find(xyTo1D(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return backwashQU.find(topRoot) == backwashQU.find(botRoot);
    }

    private int xyTo1D(int row, int col) {
        validate(row, col);
        return (row - 1) * n + col;
    }

    // The row and column indices are integers between 1 and n, where (1, 1) is the upper-left site
    private void validate(int row, int col) {
        if (row < 1 || row > n) {
            throw new IllegalArgumentException("Row index must be within 1 and " + n);
        }
        if (col < 1 || col > n) {
            throw new IllegalArgumentException("Column index must be within 1 and " + n);
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        // Tests here
    }
}