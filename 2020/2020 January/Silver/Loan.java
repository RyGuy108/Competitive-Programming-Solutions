import java.io.*;
import java.util.*;

public class Loan {
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        long N = fs.nextLong();
        long K = fs.nextLong();
        long M = fs.nextLong();

        long lo = 1;
        long hi = (long)1_000_000_000_000L; // 1e12

        while (lo < hi) {
            long mid = lo + (hi - lo + 1) / 2;
            if (valid(N, K, M, mid)) {
                lo = mid;
            } else {
                hi = mid - 1;
            }
        }
        System.out.println(lo);
    }

    static boolean valid(long n, long k, long m, long x) {
        long g = 0; // gallons given so far
        long daysLeft = k;

        while (daysLeft > 0 && g < n) {
            long y = (n - g) / x; // floor((n-g)/x)
            if (y < m) {
                long remaining = n - g;
                long needDays = (remaining + m - 1) / m; // ceil(remaining / m)
                return needDays <= daysLeft;
            }

            long maxMatch = n - x * y;

            long numDays = (maxMatch - g) / y + 1;
            if (numDays > daysLeft) numDays = daysLeft;

            g += y * numDays;
            daysLeft -= numDays;
        }

        return g >= n;
    }

    static class FastScanner {
        private final BufferedReader br;
        private StringTokenizer st;
        FastScanner(InputStream is) { br = new BufferedReader(new InputStreamReader(is)); }
        String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) {
                String line = br.readLine();
                if (line == null) return null;
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }
        long nextLong() throws IOException { return Long.parseLong(next()); }
    }
}