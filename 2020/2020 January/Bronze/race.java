import java.io.*;
import java.util.*;

public class race {
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        long K = fs.nextLong();
        int N = fs.nextInt();
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < N; i++) {
            int X = fs.nextInt();
            out.append(minTime(K, X)).append('\n');
        }
        System.out.print(out.toString());
    }

    static long minTime(long K, int X) {
        long lhs = 0L;    
        long rhs = 0L;    
        long time = 0L;
        for (long v = 1L; ; v++) {
            lhs += v;
            time++;
            if (lhs + rhs >= K) return time;
            if (v >= X) {
                rhs += v;
                time++;
                if (lhs + rhs >= K) return time;
            }
            // continue to next speed level
        }
    }

    // Fast scanner helper
    static class FastScanner {
        private final BufferedReader br;
        private StringTokenizer st;
        FastScanner(InputStream is) { br = new BufferedReader(new InputStreamReader(is)); }
        String next() throws IOException {
            while (st == null || !st.hasMoreElements()) {
                String line = br.readLine();
                if (line == null) return null;
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }
        int nextInt() throws IOException { return Integer.parseInt(next()); }
        long nextLong() throws IOException { return Long.parseLong(next()); }
    }
}