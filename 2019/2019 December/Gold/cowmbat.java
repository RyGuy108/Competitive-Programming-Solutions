import java.io.*;
import java.util.*;

public class cowmbat {
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int n = fs.nextInt();
        int m = fs.nextInt();
        int k = fs.nextInt();
        String s = fs.next();
        int[][] d = new int[m][m];
        for (int i = 0; i < m; i++) for (int j = 0; j < m; j++) d[i][j] = fs.nextInt();
        for (int c = 0; c < m; c++) for (int a = 0; a < m; a++) for (int b = 0; b < m; b++)
            if (d[a][b] > d[a][c] + d[c][b]) d[a][b] = d[a][c] + d[c][b];
        int[][] cst = new int[n+1][m];
        int[][] ps = new int[n+1][m];
        for (int i = 1; i <= n; i++) {
            int ch = s.charAt(i-1) - 'a';
            for (int j = 0; j < m; j++) {
                cst[i][j] = d[ch][j];
                ps[i][j] = ps[i-1][j] + cst[i][j];
            }
        }
        final int INF = 1_000_000_000;
        int[][] dp = new int[n+1][m];
        int[] mn = new int[n+1];
        for (int i = 0; i <= n; i++) Arrays.fill(dp[i], INF);
        Arrays.fill(mn, INF);
        mn[0] = 0;
        for (int i = 1; i <= n; i++) {
            int best = INF;
            for (int j = 0; j < m; j++) {
                int v = dp[i-1][j] + cst[i][j];
                if (i >= k) {
                    int seg = ps[i][j] - ps[i-k][j] + mn[i-k];
                    if (seg < v) v = seg;
                }
                dp[i][j] = v;
                if (v < best) best = v;
            }
            mn[i] = best;
        }
        System.out.println(mn[n]);
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
        int nextInt() throws IOException {
            int c;
            while ((c = read()) <= ' ') if (c == -1) return Integer.MIN_VALUE;
            int sign = 1;
            if (c == '-') { sign = -1; c = read(); }
            int val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sign;
        }
        String next() throws IOException {
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = read()) <= ' ') if (c == -1) return null;
            while (c > ' ') {
                sb.append((char)c);
                c = read();
            }
            return sb.toString();
        }
    }
}