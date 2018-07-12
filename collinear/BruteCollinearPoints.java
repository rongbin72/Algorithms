import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private List<LineSegment> lines;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i] == null) throw new IllegalArgumentException();
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
            }
        }

        this.lines = new ArrayList<>();
        int len = points.length;
        for (int i = 0; i < len - 3; i++) {
            for (int j = 0; j < len - 2; j++) {
                for (int k = 0; k < len - 1; k++) {
                    for (int l = 0; l < len; l++) {
                        if (points[i].slopeTo(points[j]) == points[j].slopeTo(points[k])
                                && points[j].slopeTo(points[k]) == points[k].slopeTo(points[l])) {

                            Point[] arr = { points[i], points[j], points[k], points[l] };
                            Arrays.sort(arr, arr[0].slopeOrder());
                            lines.add(new LineSegment(arr[0], arr[arr.length - 1]));
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
}
