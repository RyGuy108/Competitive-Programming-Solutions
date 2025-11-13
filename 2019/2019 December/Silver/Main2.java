import java.io.*;
import java.util.*;

public class Main2 {
    static class Cow {
        long w;
        long x;
        int d;
        Cow(long w, long x, int d) { this.w = w; this.x = x; this.d = d; }
    }

    static class TimeWeight {
        long t;
        long w;
        TimeWeight(long t, long w) { this.t = t; this.w = w; }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int N = fs.nextInt();
        long L = fs.nextLong();
        Cow[] cows = new Cow[N];
        for (int i = 0; i < N; i++) cows[i] = new Cow(fs.nextLong(), fs.nextLong(), fs.nextInt());
        Arrays.sort(cows, Comparator.comparingLong(c -> c.x));
        long totalW = 0;
        ArrayList<TimeWeight> times = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            totalW += cows[i].w;
            if (cows[i].d == -1) times.add(new TimeWeight(cows[i].x, cows[i].w));
            else times.add(new TimeWeight(L - cows[i].x, cows[i].w));
        }
        times.sort(Comparator.comparingLong(tw -> tw.t));
        long rem = totalW;
        long T = 0;
        for (TimeWeight tw : times) {
            rem -= 2L * tw.w;
            if (rem <= 0) { T = tw.t; break; }
        }
        ArrayDeque<Long> q = new ArrayDeque<>();
        long ans = 0;
        for (int i = 0; i < N; i++) {
            if (cows[i].d == 1) q.addLast(cows[i].x);
            else {
                while (!q.isEmpty() && q.peekFirst() + 2L * T < cows[i].x) q.removeFirst();
                ans += q.size();
            }
        }
        System.out.println(ans);
    }

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
            while (c > ' ') { val = val * 10 + (c - '0'); c = read(); }
            return val * sign;
        }
        int nextInt() throws IOException { return (int) nextLong(); }
    }
}