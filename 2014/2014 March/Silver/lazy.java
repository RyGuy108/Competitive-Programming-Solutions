import java.io.*;
import java.util.*;

public class lazy {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("lazy.in"));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N0 = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int newN = 2 * N0 - 1;
        long[][] mat = new long[newN][newN];
        for (int i = 0; i < N0; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N0; j++) {
                int v = Integer.parseInt(st.nextToken());
                mat[i + j][N0 - i + j - 1] = v;
            }
        }
        br.close();
        int t = (N0 + 1) % 2;
        long best = 0;
        for (int i = 0; i < newN; i++, t = 1 - t) {
            int aLo = Math.max(i - K, 0);
            int aHi = Math.min(i + K, newN - 1);
            int bLo = Math.max(t - K, 0);
            int bHi = Math.min(t + K, newN - 1);
            long sum = 0;
            for (int a = aLo; a <= aHi; a++) {
                for (int b = bLo; b <= bHi; b++) sum += mat[a][b];
            }
            if (sum > best) best = sum;
            for (int j = t + 1; j + K < newN; j++) {
                int rem = j - K - 1;
                int add = j + K;
                for (int a = aLo; a <= aHi; a++) {
                    if (rem >= 0) sum -= mat[a][rem];
                    sum += mat[a][add];
                }
                if ((j & 1) == t && sum > best) best = sum;
            }
        }
        PrintWriter pw = new PrintWriter(new FileWriter("lazy.out"));
        pw.println(best);
        pw.close();
    }
}