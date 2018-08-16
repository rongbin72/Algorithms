import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

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
        kt.insert(new Point2D(0.5, 0.6));
        kt.insert(new Point2D(0.5, 0.5));

        System.out.println(kt.contains(new Point2D(0.17, 0.19)));
        System.out.println(kt.contains(new Point2D(0.5, 0.5)));
        System.out.println(kt.contains(new Point2D(0.20, 0.24)));
        // kt.draw();
        // Iterable<Point2D> a = kt.range(new RectHV(0, 0.6, 0.5, 1));
        Point2D p = kt.nearest(new Point2D(0.55, 0.6));
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
        if (isEmpty()) {
            root = new Node(p, new RectHV(0, 0, 1, 1));
            size++;
        }
        else if (!contains(p)) {
            root = insertX(root, p, null);
            size++;
        }
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

        boolean goLeft = p.x() < node.p.x();
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

        boolean goDown = p.y() < node.p.y();
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
        StdDraw.setPenRadius(0.005);
        root.rect.draw();
        drawX(root);
    }

    private void drawX(Node node) {
        if (node == null) return;
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.005);
        StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.02);
        StdDraw.point(node.p.x(), node.p.y());
        drawY(node.lb);
        drawY(node.rt);
    }

    private void drawY(Node node) {
        if (node == null) return;
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius(0.005);
        StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.02);
        StdDraw.point(node.p.x(), node.p.y());
        drawX(node.lb);
        drawX(node.rt);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        List<Point2D> ls = new ArrayList<>();
        range(root, rect, ls);
        return ls;
    }

    private void range(Node node, RectHV rect, List<Point2D> ls) {
        if (node == null || !node.rect.intersects(rect)) return;
        if (rect.contains(node.p)) ls.add(node.p);
        range(node.lb, rect, ls);
        range(node.rt, rect, ls);
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty() || !root.rect.contains(p)) return null;
        if (contains(p)) return p;
        Point2D n = new Point2D(Double.MAX_VALUE, Double.MAX_VALUE);
        return nearest(root, p, n, Double.MAX_VALUE);
    }

    private Point2D nearest(Node node, Point2D query, Point2D nearest, double dis) {
        if (dis < node.rect.distanceSquaredTo(query)) return nearest;

        if (node.lb != null) {
            nearest = nearest(node.lb, query, nearest, dis);
            dis = nearest.distanceSquaredTo(query);
        }

        if (node.rt != null) {
            nearest = nearest(node.rt, query, nearest, dis);
            dis = nearest.distanceSquaredTo(query);
        }

        double d = node.p.distanceSquaredTo(query);
        return d < dis ? node.p : nearest;
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
