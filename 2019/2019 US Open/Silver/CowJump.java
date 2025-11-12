package Silver;

import java.io.*;
import java.util.*;

/**
 * CowJump - finds the earliest removable segment index so that no segments intersect.
 * Implements a sweep-line (plane sweep) algorithm using a TreeSet ordered by the
 * y-coordinate of each segment at the current sweep x position.
 */
public class CowJump {
    static class Point {
        long x, y;
        int segIdx;
        Point(long x, long y, int segIdx) { this.x = x; this.y = y; this.segIdx = segIdx; }
    }

    static class Segment {
        Point p, q;
        int idx;
        Segment(Point p, Point q, int idx) {
            this.p = p; this.q = q; this.idx = idx;
        }
    }

    static double sweepX; // current x position used by comparator

    static double evalY(Segment s, double x) {
        if (s.p.x == s.q.x) return s.p.y; // vertical segment: return endpoint y
        return s.p.y + (s.q.y - s.p.y) * (x - s.p.x) / (double)(s.q.x - s.p.x);
    }

    // orientation: >0 -> ccw, <0 -> cw, ==0 -> collinear
    static long orient(Point a, Point b, Point c) {
        return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
    }

    static boolean onSegment(Point a, Point b, Point c) {
        // check if c lies on segment ab (assuming collinear)
        return Math.min(a.x, b.x) <= c.x && c.x <= Math.max(a.x, b.x)
            && Math.min(a.y, b.y) <= c.y && c.y <= Math.max(a.y, b.y);
    }

    static boolean segmentsIntersect(Segment s1, Segment s2) {
        Point p1 = s1.p, q1 = s1.q, p2 = s2.p, q2 = s2.q;
        long o1 = orient(p1, q1, p2);
        long o2 = orient(p1, q1, q2);
        long o3 = orient(p2, q2, p1);
        long o4 = orient(p2, q2, q1);

        if ( (o1 > 0 && o2 < 0 || o1 < 0 && o2 > 0) && (o3 > 0 && o4 < 0 || o3 < 0 && o4 > 0) )
            return true;

        // special cases: collinear & on-segment
        if (o1 == 0 && onSegment(p1, q1, p2)) return true;
        if (o2 == 0 && onSegment(p1, q1, q2)) return true;
        if (o3 == 0 && onSegment(p2, q2, p1)) return true;
        if (o4 == 0 && onSegment(p2, q2, q1)) return true;

        return false;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int N = fs.nextInt();
        Segment[] segs = new Segment[N];
        ArrayList<Point> pts = new ArrayList<>(2*N);

        for (int i = 0; i < N; i++) {
            long x1 = fs.nextLong();
            long y1 = fs.nextLong();
            long x2 = fs.nextLong();
            long y2 = fs.nextLong();
            // create points then ensure p is the left endpoint (or smaller x; tie -> smaller y)
            Point a = new Point(x1, y1, i);
            Point b = new Point(x2, y2, i);
            Point left = a, right = b;
            if (a.x > b.x || (a.x == b.x && a.y > b.y)) {
                left = b; right = a;
            }
            segs[i] = new Segment(left, right, i);
            pts.add(left);
            pts.add(right);
        }

        pts.sort((p1, p2) -> {
            if (p1.x != p2.x) return Long.compare(p1.x, p2.x);
            return Long.compare(p1.y, p2.y);
        });

        // comparator for TreeSet uses current sweepX
        Comparator<Segment> cmp = new Comparator<Segment>() {
            public int compare(Segment s1, Segment s2) {
                if (s1.idx == s2.idx) return 0;
                double y1 = evalY(s1, sweepX);
                double y2 = evalY(s2, sweepX);
                int c = Double.compare(y1, y2);
                if (c != 0) return c;
                return Integer.compare(s1.idx, s2.idx);
            }
        };

        TreeSet<Segment> active = new TreeSet<>(cmp);
        int foundA = -1, foundB = -1;
        // sweep
        for (Point ev : pts) {
            sweepX = ev.x; // set current x before operations at this x
            int segIdx = ev.segIdx;
            Segment cur = segs[segIdx];

            // if segment is already active, we are at its right endpoint -> remove it
            if (active.contains(cur)) {
                Segment lower = active.lower(cur);
                Segment higher = active.higher(cur);
                // after removing cur, lower and higher become neighbors -> check them
                if (lower != null && higher != null && segmentsIntersect(lower, higher)) {
                    foundA = lower.idx;
                    foundB = higher.idx;
                    break;
                }
                active.remove(cur);
            } else {
                // insert segment -> check intersection with neighbors (lower and higher)
                Segment higher = active.higher(cur);
                if (higher != null && segmentsIntersect(higher, cur)) {
                    foundA = higher.idx;
                    foundB = cur.idx;
                    break;
                }
                Segment lower = active.lower(cur);
                if (lower != null && segmentsIntersect(lower, cur)) {
                    foundA = lower.idx;
                    foundB = cur.idx;
                    break;
                }
                active.add(cur);
            }
        }

        if (foundA == -1) {
            // no intersection found -> no segment needs removal (spec uncertain). Print 1 per fallback.
            System.out.println(1);
            return;
        }

        // ensure foundA < foundB
        if (foundA > foundB) {
            int t = foundA; foundA = foundB; foundB = t;
        }

        // count intersections of foundB with other segments
        int countB = 0;
        for (int i = 0; i < N; i++) {
            if (i == foundB) continue;
            if (segmentsIntersect(segs[i], segs[foundB])) countB++;
        }

        int answerIndex;
        if (countB > 1) answerIndex = foundB + 1;
        else answerIndex = foundA + 1;

        System.out.println(answerIndex);
    }


    // fast scanner
    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        FastScanner(InputStream is) { in = is; }
        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }
        long nextLong() throws IOException {
            int c;
            while ((c = read()) <= ' ') if (c == -1) return Long.MIN_VALUE;
            int sign = 1;
            if (c == '-') { sign = -1; c = read(); }
            long val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sign;
        }
        int nextInt() throws IOException { return (int) nextLong(); }
    }
}