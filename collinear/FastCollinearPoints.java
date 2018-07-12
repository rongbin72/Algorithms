import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private List<LineSegment> lines;

    public FastCollinearPoints(Point[] points) {
        check(points);

        Point[] ps = Arrays.copyOf(points, points.length);
        lines = new ArrayList<>();
        int len = ps.length;
        for (int i = 0; i < len; i++) {
            Point origin = ps[i];
            Point[] copy = Arrays.copyOf(ps, len);
            exch(copy, i, len - 1);
            List<Double> slopLs = new ArrayList<>();
            for (int j = 0; j < copy.length; j++) {
                slopLs.add(origin.slopeTo(copy[j]));
            }
            Arrays.sort(copy, 0, len - 1, origin.slopeOrder());
            slopLs = new ArrayList<>();
            for (int j = 0; j < copy.length; j++) {
                slopLs.add(origin.slopeTo(copy[j]));
            }
            int cnt = 0;
            for (int j = 0; j < len - 2; j++) {
                if (origin.slopeTo(copy[j]) != origin.slopeTo(copy[j + 1])) {
                    if (cnt >= 4) {
                        lines.add(new LineSegment(origin, copy[j]));
                    }
                }
                else cnt++;
            }
            if (cnt >= 4) lines.add(new LineSegment(origin, copy[len - 2]));
        }
    }

    private void exch(Point[] points, int i, int j) {
        Point tmp = points[i];
        points[i] = points[j];
        points[j] = tmp;
    }

    public int numberOfSegments() {
        return lines.size();
    }

    public LineSegment[] segments() {
        return lines.toArray(new LineSegment[0]);
    }

    // could use set to check in O(n) time, but use O(n) extra space
    // Since set has not mentioned in the course yet, not use it in this assignment
    private void check(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
            }
        }
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
