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
        lines = new ArrayList<>();
        int len = points.length;
        for (int i = 0; i < len; i++) {
            Point origin = points[i];
            Point[] copy = Arrays.copyOf(points, len);
            exch(copy, i, 0);
            Arrays.sort(copy, 1, len, origin.slopeOrder());
            int cnt = 0;
            for (int j = 1; j < len - 1; j++) {
                if (origin.slopeTo(copy[j]) == origin.slopeTo(copy[j + 1]))
                    cnt++;
                else if (cnt >= 2) {
                    lines.add(new LineSegment(origin, copy[j]));
                    cnt = 0;
                }
                else cnt = 0;
            }
            if (cnt >= 2) lines.add(new LineSegment(origin, copy[len - 1]));
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
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i] == null || points[i + 1] == null) throw new IllegalArgumentException();

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
