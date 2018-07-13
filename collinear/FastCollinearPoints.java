import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private List<LineSegment> lines;

    public FastCollinearPoints(Point[] points) {
        if (points==null) throw new IllegalArgumentException();
        lines = new ArrayList<>();
        int len = points.length;
        for (int i = 0; i < len; i++) {
            Point[] copy = Arrays.copyOf(points, len);
            Point origin = copy[i];
            Arrays.sort(copy, Point::compareTo);
            Arrays.sort(copy, origin.slopeOrder());
            // assert origin.compareTo(copy[0]) == 0;
            /*
            -INFINITE    1       2 3 4 5
            ^            ^       ^
            origin       next    end
             */
            for (int next = 1, end = 2; end < len; end++) {
                while (end < len)  {
                    if (copy[next] == null || copy[end] == null)
                        throw new IllegalArgumentException();
                    if (origin.slopeTo(copy[next]) == origin.slopeTo(copy[end]))
                        end++;
                    else
                        break;
                }
                // Since points are sorted by x y coordinates
                // the longest collinear line must start with the smallest point
                if (end - next >= 3 && origin.compareTo(copy[next]) < 0)
                    lines.add(new LineSegment(origin, copy[end - 1]));
                next = end;
            }
        }
    }

    public int numberOfSegments() {
        return lines.size();
    }

    public LineSegment[] segments() {
        return lines.toArray(new LineSegment[0]);
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
