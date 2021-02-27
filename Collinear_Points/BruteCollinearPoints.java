import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BruteCollinearPoints {

  private final LineSegment[] lineSegments;

  // finds all line segments containing 4 points
  public BruteCollinearPoints(Point[] points) {
    checkNull(points);
    Arrays.sort(points);
    checkDuplicates(points);

    int N = points.length;

    List<LineSegment> lines = new LinkedList<>();
    for (int a = 0; a < N - 3; a++) {
      Point pta = points[a];
      for (int b = a+1; b < N - 2; b++) {
        Point ptb = points[b];
        double slopeAB = pta.slopeTo(ptb);
        for (int c = b+1; c < N - 1; c++) {
          Point ptc = points[c];
          double slopeAC = pta.slopeTo(ptc);
          if (slopeAB == slopeAC) {

            for (int d = c+1; d < N; d++) {
              Point ptd = points[d];
              double slopeAD = pta.slopeTo(ptd);

              if (slopeAB == slopeAD) {
                lines.add(new LineSegment(pta, ptd));
              }
            }
          }
        }
      }
    }
    lineSegments = lines.toArray(new LineSegment[0]);
  }

  private void checkNull(Point[] points) {
    if (points == null) {
      throw new IllegalArgumentException("Array arg must not be null");
    }
    for (int i = 0; i < points.length; i++) { 
      if (points[i] == null) { 
        throw new IllegalArgumentException("Array must not contain null points");
      }
    }
  }

  private void checkDuplicates(Point[] points) {
    // Array must be sorted already in order to for a single loop check to work.
    assert isSorted(points);

    for (int i = 0; i < points.length - 1; i++) { 
      if (points[i].compareTo(points[i+1]) == 0) {
        throw new IllegalArgumentException("Array must not contain duplicate points");
      }
    }
  }

  // is v < w ?
  private static boolean less(Comparable v, Comparable w) {
    return v.compareTo(w) < 0;
  }

  // Check if array is sorted - useful for debugging.
  private static boolean isSorted(Comparable[] a) {
    for (int i = 1; i < a.length; i++)
      if (less(a[i], a[i-1])) return false;
    return true;
  }

  // the number of line segments
  public int numberOfSegments() {
    return this.lineSegments.length;
  }      

  // the line segments
  public LineSegment[] segments() {
    return this.lineSegments;
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
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
  }
}
