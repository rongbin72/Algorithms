import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF grid;
    private boolean[] isOpen;
    private final int width;  // width of grid (n)
    private final int gridSize;  // total number of sites (n * n)
    private int openSites;  // number of open sites

    /**
     * create n-by-n grid, with all sites blocked
     *
     * @param n width of the gird
     */
    public Percolation(int n) {
        openSites = 0;
        width = n;
        gridSize = n * n;
        // 2 Means two virtual sites at the top and botton
        grid = new WeightedQuickUnionUF(gridSize + 2);
        // size n + 1 for convience of access (index between [1, n])
        isOpen = new boolean[gridSize + 2];
        for (int i = 0; i < gridSize + 2; i++) {
            isOpen[i] = false;
        }
        // open two virtual sites
        isOpen[0] = true;
        isOpen[gridSize + 1] = true;
    }

    /**
     * open site (row, col) if it is not open already
     *
     * @param row row index
     * @param col column index
     */
    public void open(int row, int col) {
        validateIndex(row, col);
        if (!isOpen(row, col)) {
            int index = flatten(row, col);
            isOpen[index] = true;
            unionNeighbors(row, col);
            openSites++;
        }
    }

    /**
     * is site (row, col) open?
     *
     * @param row row index
     * @param col column index
     * @return bool
     */
    public boolean isOpen(int row, int col) {
        validateIndex(row, col);
        int index = flatten(row, col);
        return isOpen[index];
    }

    /**
     * Is site (row, col) full? is site connected to the top row
     *
     * @param row row index
     * @param col column index
     * @return bool
     */
    public boolean isFull(int row, int col) {
        validateIndex(row, col);
        return grid.connected(0, flatten(row, col));
    }


    /**
     * number of open sites
     */
    public int numberOfOpenSites() {
        return openSites;
    }

    /**
     * Does the system percolate ? => Is two virtual sites connected ?
     *
     * @return bool
     */
    public boolean percolates() {
        return grid.connected(0, gridSize + 1);
    }

    /**
     * Flatten 2-D index to 1-D index in range [1, gridSize]
     *
     * @param row row index
     * @param col column index
     * @return 1-D index
     */
    private int flatten(int row, int col) {
        return (row - 1) * width + col;
    }

    private void unionNeighbors(int row, int col) {
        // connect to nerigbors and top virtual site if in top row
        int currentIndex = flatten(row, col);
        if (row == 1) grid.union(currentIndex, 0);  // connect to top virtual site
        if (row == width)
            grid.union(currentIndex, gridSize + 1);  // connetc to botton virtual site;

        tryUnion(currentIndex, row - 1, col);  // top
        tryUnion(currentIndex, row + 1, col);  // botton
        tryUnion(currentIndex, row, col - 1);  // left
        tryUnion(currentIndex, row, col + 1);  // right

    }

    private void tryUnion(int current, int row, int col) {
        if (inGrid(row, col) && isOpen(row, col) && !grid.connected(current, flatten(row, col))) {
            grid.union(current, flatten(row, col));
        }
    }

    private void validateIndex(int row, int col) {
        if (row < 1 || col < 0 || row > width || col > width) {
            System.err.printf("Index out of bound => %d:%d", row, col);
            throw new IllegalArgumentException();
        }
    }

    private boolean inGrid(int row, int col) {
        return 1 <= row && row <= width &&
                1 <= col && col <= width;
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
