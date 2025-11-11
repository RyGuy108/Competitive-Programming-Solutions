import java.io.*;
import java.util.*;

public class shell {
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int N = fs.nextInt();
        int[] A = new int[N];
        int[] B = new int[N];
        int[] G = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = fs.nextInt();
            B[i] = fs.nextInt();
            G[i] = fs.nextInt();
        }
        int best = 0;
        for (int start = 1; start <= 3; start++) {
            int cur = start;
            int correct = 0;
            for (int i = 0; i < N; i++) {
                if (A[i] == cur) cur = B[i];
                else if (B[i] == cur) cur = A[i];
                if (cur == G[i]) correct++;
            }
            best = Math.max(best, correct);
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
    }
}