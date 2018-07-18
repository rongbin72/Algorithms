public class Board {
    private int[][] tiles;

    public Board(int[][] blocks) {

    }

    public int dimension() {

    }

    public int hamming() {

    }

    public int manhattan() {

    }

    public boolean isGoal() {

    }

    public Board twin() {

    }

    public boolean equals(Object y) {

    }

    public Iterable<Board> neighbors() {

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

    public static void main(String[] args) {
    }
}
