import java.io.*;
import java.util.*;

public class Wormsort {

    static class DSU {
        int[] p, r;
        DSU(int n) { p = new int[n]; r = new int[n]; for (int i = 0; i < n; i++) p[i] = i; }
        int find(int a) { return p[a] == a ? a : (p[a] = find(p[a])); }
        void union(int a, int b) {
            a = find(a); b = find(b);
            if (a == b) return;
            if (r[a] < r[b]) p[a] = b;
            else if (r[b] < r[a]) p[b] = a;
            else { p[b] = a; r[a]++; }
        }
    }

    static class Edge {
        int u, v;
        int w;
        Edge(int u, int v, int w) { this.u = u; this.v = v; this.w = w; }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int n = fs.nextInt();
        int m = fs.nextInt();

        int[] perm = new int[n];
        for (int i = 0; i < n; i++) {
            perm[i] = fs.nextInt() - 1; // zero-based
        }

        Edge[] edges = new Edge[m];
        int maxW = 0;
        for (int i = 0; i < m; i++) {
            int a = fs.nextInt() - 1;
            int b = fs.nextInt() - 1;
            int w = fs.nextInt();
            edges[i] = new Edge(a, b, w);
            if (w > maxW) maxW = w;
        }

        // Quick check: if already sorted, answer is -1
        boolean alreadySorted = true;
        for (int i = 0; i < n; i++) if (perm[i] != i) { alreadySorted = false; break; }
        if (alreadySorted) {
            System.out.println(-1);
            return;
        }

        // Binary search on width
        int lo = 1, hi = maxW, ans = -1;
        while (lo <= hi) {
            int mid = lo + ((hi - lo) >>> 1);
            if (valid(n, perm, edges, mid)) {
                ans = mid;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }

        if (ans <= 0) System.out.println(-1);
        else System.out.println(ans);
    }

    static boolean valid(int n, int[] perm, Edge[] edges, int minW) {
        DSU dsu = new DSU(n);
        for (Edge e : edges) {
            if (e.w >= minW) dsu.union(e.u, e.v);
        }
        for (int i = 0; i < n; i++) {
            if (dsu.find(i) != dsu.find(perm[i])) return false;
        }
        return true;
    }

    // Fast input
    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1<<16];
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
            while ((c = read()) <= ' ') {
                if (c == -1) return Integer.MIN_VALUE;
            }
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