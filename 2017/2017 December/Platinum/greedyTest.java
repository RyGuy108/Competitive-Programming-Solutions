import java.io.*;
import java.util.*;

/**
 * Java translation of the provided C++ solution.
 * Reads greedy.in and writes greedy.out
 */
public class greedyTest {
    // same upper bound as the C++ NMAX
    static final int NMAX = 2000005;

    static int[] c = new int[NMAX];
    static int[] num = new int[NMAX + 1];

    // check whether with k steps the condition "all_recv" holds
    static boolean all_recv(int k, int n) {
        // reset counts for 1..n
        Arrays.fill(num, 1, n + 1, 0);

        // count frequencies of c[0 .. k-2] (i < k-1)
        for (int i = 0; i < k - 1; i++) {
            int idx = c[i];
            // idx should be in 1..n according to how c is filled in main
            if (idx >= 1 && idx <= n) num[idx]++;
        }

        int total = 0;
        for (int i = 1; i <= n; i++) {
            total += num[i];
            if (total >= i) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(new FileInputStream("greedy.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("greedy.out")));

        int n = fs.nextInt();
        for (int i = 0; i < n; i++) {
            int d = fs.nextInt();
            // keep a check similar to the C++ assert
            if (d < 0 || d >= n) throw new IllegalArgumentException("d out of range");
            c[i] = n - d; // same transformation as C++ code
        }

        // binary search for largest k such that all_recv(k, n) is true
        int lo = 1;
        int hi = n + 1;
        while (hi > lo + 1) {
            int mid = (lo + hi) >>> 1;
            if (all_recv(mid, n)) {
                lo = mid;
            } else {
                hi = mid;
            }
        }

        int ans = lo;
        pw.println(n - ans);

        pw.close();
        fs.close();
    }

    // ---------- Fast scanner (fast int reading) ----------
    static class FastScanner implements Closeable {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(InputStream is) {
            in = is;
        }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        int nextInt() throws IOException {
            int c = read();
            while (c <= ' ') {
                if (c == -1) throw new EOFException();
                c = read();
            }
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }
            int val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sign;
        }

        @Override
        public void close() throws IOException {
            in.close();
        }
    }
}