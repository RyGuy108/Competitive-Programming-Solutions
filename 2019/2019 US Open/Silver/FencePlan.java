package Silver;

import java.io.*;
import java.util.*;

public class FencePlan {
    static final int INF = 1_000_000_000;
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int N = fs.nextInt();
        int M = fs.nextInt();
        int[] xs = new int[N];
        int[] ys = new int[N];
        for (int i = 0; i < N; i++) {
            xs[i] = fs.nextInt();
            ys[i] = fs.nextInt();
        }
        ArrayList<Integer>[] adj = new ArrayList[N];
        for (int i = 0; i < N; i++) adj[i] = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            int a = fs.nextInt() - 1;
            int b = fs.nextInt() - 1;
            adj[a].add(b);
            adj[b].add(a);
        }
        boolean[] vis = new boolean[N];
        int best = INF;
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < N; i++) {
            if (vis[i]) continue;
            int minX = INF, maxX = -INF, minY = INF, maxY = -INF;
            stack.push(i);
            vis[i] = true;
            while (!stack.isEmpty()) {
                int u = stack.pop();
                int ux = xs[u], uy = ys[u];
                if (ux < minX) minX = ux;
                if (ux > maxX) maxX = ux;
                if (uy < minY) minY = uy;
                if (uy > maxY) maxY = uy;
                for (int v : adj[u]) {
                    if (!vis[v]) {
                        vis[v] = true;
                        stack.push(v);
                    }
                }
            }
            int peri = 2 * ((maxX - minX) + (maxY - minY));
            if (peri < best) best = peri;
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