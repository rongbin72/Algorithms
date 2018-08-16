import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {
    private int size;
    private Node root;

    public KdTree() {
        size = 0;
    }

    public static void main(String[] args) {
        KdTree kt = new KdTree();
        kt.insert(new Point2D(0.5, 0.5));
        kt.insert(new Point2D(0.25, 0.5));
        kt.insert(new Point2D(0.75, 0.5));
        kt.insert(new Point2D(0.25, 0.25));
        kt.insert(new Point2D(0.25, 0.75));

        System.out.println(kt.contains(new Point2D(0.17, 0.19)));
        System.out.println(kt.contains(new Point2D(0.5, 0.5)));
        System.out.println(kt.contains(new Point2D(0.20, 0.24)));
        System.out.println(kt.contains(new Point2D(0.20, 0.24)));
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    /**
     * Mutual recursion,2-state FSM, starts with insertX(root)
     *
     * @param p Point2D
     */
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) root = new Node(p, new RectHV(0, 0, 1, 1));
        else root = insertX(root, p, null);
        size++;
    }

    /**
     * Compare X coordinate
     *
     * @param node Node
     * @param p    Point2D
     * @param rect RectHV for next Node
     * @return Root node after insertion
     */
    private Node insertX(Node node, Point2D p, RectHV rect) {
        if (node == null) return new Node(p, rect);
        if (p.equals(node.p)) return node;

        boolean goLeft = p.x() <= node.p.x();
        if (goLeft) node.lb = insertY(node.lb, p,
                                      new RectHV(node.rect.xmin(), node.rect.ymin(),
                                                 node.p.x(), node.rect.ymax()));
        else node.rt = insertY(node.rt, p,
                               new RectHV(node.p.x(), node.rect.ymin(),
                                          node.rect.xmax(), node.rect.ymax()));
        return node;
    }

    /**
     * Compare Y coordinate
     *
     * @param node Node
     * @param p    Point2D
     * @param rect RectHV for next Node
     * @return Root node after insertion
     */
    private Node insertY(Node node, Point2D p, RectHV rect) {
        if (node == null) return new Node(p, rect);
        if (p.equals(node.p)) return node;

        boolean goDown = p.y() <= node.p.y();
        if (goDown) node.lb = insertX(node.lb, p, new RectHV(node.rect.xmin(), node.rect.ymin(),
                                                             node.rect.xmax(), node.p.y()));
        else node.rt = insertX(node.rt, p,
                               new RectHV(node.rect.xmin(), node.p.y(),
                                          node.rect.xmax(), node.rect.ymax()));
        return node;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return false;
        return getX(root, p) != null;
    }

    private Node getX(Node node, Point2D p) {
        if (node == null) return null;
        if (node.p.equals(p)) return node;
        boolean goLeft = p.x() <= node.p.x();
        if (goLeft) return getY(node.lb, p);
        else return getY(node.rt, p);
    }

    private Node getY(Node node, Point2D p) {
        if (node == null) return null;
        if (node.p.equals(p)) return node;
        boolean goDown = p.y() <= node.p.y();
        if (goDown) return getX(node.lb, p);
        else return getX(node.rt, p);
    }

    public void draw() {

    }

    public Iterable<Point2D> range(RectHV rect) {
        return null;
    }

    public Point2D nearest(Point2D p) {
        return null;
    }

    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;

        public Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
        }
    }
}
