import java.io.*;
import java.util.*;

public class time {
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int N = fs.nextInt();
        int M = fs.nextInt();
        long C = fs.nextLong();
        long[] val = new long[N+1];
        for (int i = 1; i <= N; i++) val[i] = fs.nextLong();
        int[] U = new int[M];
        int[] V = new int[M];
        for (int i = 0; i < M; i++) {
            U[i] = fs.nextInt();
            V[i] = fs.nextInt();
        }
        final int MAXT = 1001;
        long NEG = Long.MIN_VALUE / 4;
        long[][] dp = new long[2][N+1];
        for (int i = 0; i < 2; i++) Arrays.fill(dp[i], NEG);
        dp[0][1] = 0;
        long best = 0;
        for (int t = 1; t <= 1000; t++) {
            int p = t & 1;
            int q = 1 - p;
            Arrays.fill(dp[p], NEG);
            for (int e = 0; e < M; e++) {
                int a = U[e];
                int b = V[e];
                if (dp[q][a] > NEG) dp[p][b] = Math.max(dp[p][b], dp[q][a] + val[b]);
            }
            if (dp[p][1] > NEG) best = Math.max(best, dp[p][1] - C * 1L * t * t);
        }
        System.out.println(best);
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
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sign;
        }
        int nextInt() throws IOException { return (int) nextLong(); }
    }
}