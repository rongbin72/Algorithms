import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {
    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;
    }

    public KdTree() {

    }

    public boolean isEmpty() {
        return true;
    }

    public int size() {
        return 0;
    }

    public void insert(Point2D point2D) {

    }

    public boolean contains(Point2D p) {
        return true;
    }

    public void draw() {

    }

    public Iterable<Point2D> range(RectHV rect) {
        return null;
    }

    public Point2D nearest(Point2D p) {
        return null;
    }

}
