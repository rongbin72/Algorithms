import edu.princeton.cs.algs4.Stack;

import java.util.Iterator;

public class Board {
    private final int[][] tiles;
    private final int n;
    private final int hamming, manhattan;
    private int x, y;  // coordinates of the blank

    public Board(int[][] blocks) {
        tiles = blocks;
        n = blocks.length;

        // compute manhattan
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    x = i;
                    y = j;
                    continue;
                } // ignore the blank
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

        return false;
    }

    public Board twin() {

        return null;
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

    private boolean inBoard(int x, int y) {
        return (0 <= x && x < n) && (0 <= y && y < n);
    }

    public static void main(String[] args) {
        Board a = new Board(new int[][] { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } });
        Board b = new Board(new int[][] { { 0, 1 }, { 3, 2 } });
        System.out.println(a.manhattan());
        System.out.println(5 % 3);
        Iterator<Board> iter = a.neighbors().iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next().toString());
        }
        System.out.println();
    }
}
