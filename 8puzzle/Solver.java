import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private final Stack<Board> solution;
    private final boolean isSolvable;
    private final int moves;

    private class Node implements Comparable<Node> {
        private final Board board;
        private final int moves;
        private final int manhattan;
        private final Node pre;

        Node(Board init, Node p, int m) {
            board = init;
            moves = m;
            manhattan = board.manhattan();
            pre = p;
        }

        @Override
        public int compareTo(Node that) {
            int diff = (this.manhattan + this.moves) - (that.manhattan + that.moves);
            return diff == 0 ? this.manhattan - that.manhattan : diff;
        }
    }

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        solution = new Stack<>();
        MinPQ<Node> pq = new MinPQ<>();
        MinPQ<Node> pqTwin = new MinPQ<>();
        pq.insert(new Node(initial, null, 0));
        pqTwin.insert(new Node(initial.twin(), null, 0));

        Node searchNode = pq.delMin();
        Node searchNodeTwin = pqTwin.delMin();
        while (!(searchNode.board.isGoal() || searchNodeTwin.board.isGoal())) {
            for (Board neighbor : searchNode.board.neighbors())
                if (searchNode.pre == null || !neighbor.equals(searchNode.pre.board))
                    pq.insert(new Node(neighbor, searchNode, searchNode.moves + 1));
            searchNode = pq.delMin();

            for (Board neighbor : searchNodeTwin.board.neighbors())
                if (searchNodeTwin.pre == null || !neighbor.equals(searchNodeTwin.pre.board))
                    pqTwin.insert(new Node(neighbor, searchNodeTwin, searchNodeTwin.moves + 1));
            searchNodeTwin = pqTwin.delMin();
        }

        isSolvable = searchNode.board.isGoal();
        moves = isSolvable ? searchNode.moves : -1;

        solution.push(searchNode.board);
        while (searchNode.pre != null) {
            solution.push(searchNode.pre.board);
            searchNode = searchNode.pre;
        }
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
        In in = new In("data/" + args[0]);
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
                StdOut.println(board);
            }

        }
    }
}
