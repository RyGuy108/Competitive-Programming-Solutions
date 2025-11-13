import java.io.*;
import java.util.*;

public class photo {
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int N = fs.nextInt();
        int[] b = new int[N-1];
        for (int i = 0; i < N-1; i++) b[i] = fs.nextInt();

        // Precompute d[i] = b[i] - b[i-1] for i>=1 (0-based: d[1] = b[1]-b[0])
        int[] d = new int[N];
        for (int i = 2; i < N; i++) d[i] = b[i-1] - b[i-2];

        int[] ans = new int[N];
        boolean[] used = new boolean[N+1];

        for (int a1 = 1; a1 <= N; a1++) {
            Arrays.fill(used, false);

            ans[0] = a1;
            ans[1] = b[0] - a1;

            // quickly prune if a2 out of range
            if (ans[1] <= 0 || ans[1] > N) continue;
            boolean bad = false;

            for (int i = 2; i < N; i++) {
                // ans[i] = ans[i-2] + d[i]
                long v = (long)ans[i-2] + d[i];
                if (v <= 0 || v > N) { bad = true; break; }
                ans[i] = (int) v;
            }
            if (bad) continue;

            // check permutation uniqueness
            boolean ok = true;
            for (int i = 0; i < N; i++) {
                int v = ans[i];
                if (v <= 0 || v > N) { ok = false; break; }
                if (used[v]) { ok = false; break; }
                used[v] = true;
            }
            if (ok) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < N; i++) {
                    if (i > 0) sb.append(' ');
                    sb.append(ans[i]);
                }
                System.out.println(sb.toString());
                return;
            }
        }
    }

    // Fast scanner helper
    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;
        FastScanner(InputStream is) {
            br = new BufferedReader(new InputStreamReader(is));
        }
        String next() throws IOException {
            while (st == null || !st.hasMoreElements()) {
                String line = br.readLine();
                if (line == null) return null;
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }
        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}