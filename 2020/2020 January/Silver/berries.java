import java.io.*;
import java.util.*;

/**
 * Java solution for the "berries" problem (USACO).
 */
public class berries {
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int N = fs.nextInt();
        int K = fs.nextInt();
        int[] A = new int[N];
        int maxA = 0;
        for (int i = 0; i < N; i++) {
            A[i] = fs.nextInt();
            if (A[i] > maxA) maxA = A[i];
        }

        int half = K / 2;       
        long best = 0L;

        for (int b = 1; b <= maxA; b++) {
            // how many full b-sized baskets we can make in total
            int full = 0;
            for (int i = 0; i < N; i++) full += A[i] / b;


            if (full < half) break;

            if (full >= K) {
                best = Math.max(best, 1L * b * half);
                continue;
            }

            Integer[] rem = new Integer[N];
            for (int i = 0; i < N; i++) rem[i] = A[i] % b;
            Arrays.sort(rem, Collections.reverseOrder());


            long cur = 1L * b * (full - half);   
            int need = K - full;
            for (int i = 0; i < need && i < N; i++) cur += rem[i];

            best = Math.max(best, cur);
        }

        System.out.println(best);
    }

    // Simple fast scanner
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
    }
}