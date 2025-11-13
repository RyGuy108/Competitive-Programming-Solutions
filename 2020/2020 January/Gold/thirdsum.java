import java.io.*;
import java.util.*;

public class thirdsum {
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner("threesum.in");
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("threesum.out")));
        int N = fs.nextInt();
        int Q = fs.nextInt();
        int[] A = new int[N];
        for (int i = 0; i < N; i++) A[i] = fs.nextInt();
        final int OFFSET = 1000000;
        final int RANGE = 2 * OFFSET + 1;
        int[] cnt = new int[RANGE];
        long[] ans = new long[N * N];
        for (int i = N - 1; i >= 0; i--) {
            for (int j = i + 1; j < N; j++) {
                int need = OFFSET - (A[i] + A[j]);
                long found = 0;
                if (need >= 0 && need < RANGE) found = cnt[need];
                ans[i * N + j] = found;
                cnt[A[j] + OFFSET]++;
            }
            for (int j = i + 1; j < N; j++) cnt[A[j] + OFFSET]--;
        }
        for (int i = N - 1; i >= 0; i--) {
            for (int j = i + 1; j < N; j++) {
                long a = (i + 1 < N) ? ans[(i + 1) * N + j] : 0;
                long b = (j - 1 >= 0) ? ans[i * N + (j - 1)] : 0;
                long c = (i + 1 < N && j - 1 >= 0) ? ans[(i + 1) * N + (j - 1)] : 0;
                ans[i * N + j] += a + b - c;
            }
        }
        for (int qi = 0; qi < Q; qi++) {
            int l = fs.nextInt() - 1;
            int r = fs.nextInt() - 1;
            out.println(ans[l * N + r]);
        }
        out.close();
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;
        FastScanner(String file) throws Exception {
            br = new BufferedReader(new FileReader(file));
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