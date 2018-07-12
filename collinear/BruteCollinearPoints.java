import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private List<LineSegment> lines;

    public BruteCollinearPoints(Point[] points) {
        check(points);

        Point[] ps = Arrays.copyOf(points, points.length);
        this.lines = new ArrayList<>();
        Arrays.sort(ps, Point::compareTo);
        int len = ps.length;
        for (int i = 0; i < len - 3; i++) {
            for (int j = i + 1; j < len - 2; j++) {
                for (int k = j + 1; k < len - 1; k++) {
                    for (int l = k + 1; l < len; l++) {
                        if (ps[i].slopeTo(ps[j]) == ps[j].slopeTo(ps[k])
                                && ps[j].slopeTo(ps[k]) == ps[k].slopeTo(ps[l])) {
                            lines.add(new LineSegment(ps[i], ps[l]));
                        }
                    }
                }
            }
        }
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
        for(Point p : points) if (p == null) throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
            }
        }
    }
}
