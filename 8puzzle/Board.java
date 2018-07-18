import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private final int[][] tiles;
    private final int n;
    private final int hamming, manhattan;
    private int x, y;  // coordinates of the blank

    public Board(int[][] blocks) {
        if (blocks == null) throw new IllegalArgumentException();

        tiles = blocks;
        n = blocks.length;

        // compute manhattan
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    x = i;
                    y = j;
                    continue; // ignore the blank
                }
                // expected row and col of this nulber
                int row = tiles[i][j] / n + 1;
                int col = tiles[i][j] % n == 0 ? n : tiles[i][j] % n;
                sum += Math.abs(row - (i + 1)) + Math.abs(col - (j + 1));
            }
        }
        manhattan = sum;

        // compute hamming
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != i * n + j + 1)
                    cnt++;  // empty tild adds into it
            }
        }
        hamming = cnt - 1;  // ignore blank tild
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        return hamming;
    }

    public int manhattan() {
        return manhattan;
    }

    public boolean isGoal() {
        return hamming == 0;
    }

    public Board twin() {
        int x1 = StdRandom.uniform(0, n);
        int y1 = StdRandom.uniform(0, n);
        while (x1 == x && y1 == y) {
            x1 = StdRandom.uniform(0, n);
            y1 = StdRandom.uniform(0, n);
        }

        int x2 = StdRandom.uniform(0, n);
        int y2 = StdRandom.uniform(0, n);
        while (x2 == x && y2 == y || x1 == x2 && y1 == y2) {
            x2 = StdRandom.uniform(0, n);
            y2 = StdRandom.uniform(0, n);
        }

        int[][] arr = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                arr[i][j] = tiles[i][j];
            }
        }
        arr[x1][x1] = tiles[x2][y2];
        arr[x2][y2] = tiles[x1][y1];
        return new Board(arr);
    }

    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != that.tiles[i][j])
                    return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<>();
        // left
        if (inBoard(x - 1, y))
            neighbors.push(new Board(buildArray(x - 1, y)));
        // top
        if (inBoard(x, y - 1))
            neighbors.push(new Board(buildArray(x, y - 1)));
        // right
        if (inBoard(x + 1, y))
            neighbors.push(new Board(buildArray(x + 1, y)));
        // botton
        if (inBoard(x, y + 1))
            neighbors.push(new Board(buildArray(x, y + 1)));

        return neighbors;
    }

    /**
     * Copy tilds and exchange (x, y) and (row, col)
     *
     * @param row row
     * @param col row
     */
    private int[][] buildArray(int row, int col) {
        int[][] arr = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                arr[i][j] = tiles[i][j];
            }
        }
        arr[row][col] = tiles[x][y];
        arr[x][y] = tiles[row][col];

        return arr;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    /**
     * Check whether a coordinates is valid
     *
     * @param x x
     * @param y x
     * @return boolean
     */
    private boolean inBoard(int x, int y) {
        return (0 <= x && x < n) && (0 <= y && y < n);
    }

    public static void main(String[] args) {
        Board a = new Board(new int[][] { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } });
        for (Board board : a.neighbors()) {
            System.out.println(board.toString());
        }
        System.out.println();
    }
}
