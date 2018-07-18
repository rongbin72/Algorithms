import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private Queue<Board> solution;
    private boolean isSolvable;
    private int moves;

    private class Node {
        private Board board;
        private Board pre;
        private int moves;

        Node(Board init, Board p, int m) {
            pre = p;
            board = init;
            moves = m;
        }
    }

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        moves = 0;
        solution = new Queue<>();

        MinPQ<Node> pq = new MinPQ<>(
                (o1, o2) -> (o1.board.manhattan() + o1.moves) - (o2.board.manhattan() + o2.moves));
        pq.insert(new Node(initial, null, moves));

        MinPQ<Node> pqTwin = new MinPQ<>(
                (o1, o2) -> (o1.board.manhattan() + o1.moves) - (o2.board.manhattan() + o2.moves));
        pqTwin.insert(new Node(initial.twin(), null, moves));

        Node searchNode = pq.delMin();
        Node searchNodeTwin = pqTwin.delMin();
        while (!(searchNode.board.isGoal() || searchNodeTwin.board.isGoal())) {
            moves++;
            for (Board neighbor : searchNode.board.neighbors())
                if (!neighbor.equals(searchNode.pre))
                    pq.insert(new Node(neighbor, searchNode.board, moves));
            searchNode = pq.delMin();

            for (Board neighbor : searchNodeTwin.board.neighbors())
                if (!neighbor.equals(searchNodeTwin.pre))
                    pqTwin.insert(new Node(neighbor, searchNodeTwin.board, moves));
            searchNodeTwin = pqTwin.delMin();
            solution.enqueue(searchNode.board);
        }

        isSolvable = searchNode.board.isGoal();
    }

    public boolean isSolvable() {
        return isSolvable;
    }

    public int moves() {
        return moves;
    }

    public Iterable<Board> solution() {
        return isSolvable ? solution : null;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board.manhattan());
                StdOut.println(board);
            }

        }
    }
}
