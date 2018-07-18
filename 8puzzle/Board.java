import edu.princeton.cs.algs4.Stack;

import java.util.Arrays;

public class Board {
    private final int[][] tiles;
    private final int n;
    private final int hamming, manhattan;
    private int x, y;  // coordinates of the blank

    public Board(int[][] blocks) {
        if (blocks == null) throw new IllegalArgumentException();

        n = blocks.length;
        tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = blocks[i][j];
            }
        }
        // compute manhattan
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    x = i;
                    y = j;
                    continue; // ignore the blank
                }
                // expected row and col of this number
                int r = tiles[i][j] % n;
                int row = r == 0 ? tiles[i][j] / n - 1 : tiles[i][j] / n;
                int col = r == 0 ? n - 1 : r - 1;
                sum += Math.abs(row - i) + Math.abs(col - j);
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
        int[][] arr = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                arr[i][j] = tiles[i][j];
            }
        }

        if (arr[0][0] == 0) exch(arr, 0, 1, 1, 0);
        else if (arr[0][1] == 0) exch(arr, 0, 0, 1, 0);
        else exch(arr, 0, 0, 0, 1);

        return new Board(arr);
    }

    private void exch(int[][] arr, int x1, int y1, int x2, int y2) {
        arr[x1][y1] = tiles[x2][y2];
        arr[x2][y2] = tiles[x1][y1];
    }

    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.n != that.n) return false;
        return Arrays.deepEquals(this.tiles, that.tiles);
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
    }
}
