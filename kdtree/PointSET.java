import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> ps;

    public PointSET() {
        ps = new TreeSet<>();
    }

    public boolean isEmpty() {
        return ps.isEmpty();
    }

    public int size() {
        return ps.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        ps.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return ps.contains(p);
    }

    public void draw() {
        for (Point2D p : ps) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();

        List<Point2D> ls = new ArrayList<>();
        for (Point2D p : ps) {
            if (rect.contains(p)) {
                ls.add(p);
            }
        }
        return ls;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        Point2D nearest = null;
        for (Point2D pt : ps) {
            if (nearest == null || pt.distanceTo(p) < nearest.distanceTo(p)) {
                nearest = pt;
            }
        }
        return nearest;
    }
}
