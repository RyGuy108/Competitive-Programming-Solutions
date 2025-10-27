import java.io.*;
import java.util.*;

public class marathon {
    static int[] x, y;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("marathon.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("marathon.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        x = new int[n];
        y = new int[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            x[i] = Integer.parseInt(st.nextToken());
            y[i] = Integer.parseInt(st.nextToken());
        }

        final int INF = 1 << 30;
        // dp[s][j] = minimal distance to reach checkpoint j having skipped exactly s checkpoints
        int[][] dp = new int[k + 1][n];
        for (int s = 0; s <= k; s++) Arrays.fill(dp[s], INF);
        dp[0][0] = 0;

        // transitions: from (i,j) jump to l>j, skipping (l-j-1) checkpoints
        for (int i = 0; i <= k; i++) {
            for (int j = 0; j < n; j++) {
                if (dp[i][j] == INF) continue;
                for (int l = j + 1; l < n && i + (l - j - 1) <= k; l++) {
                    int nextI = i + (l - j - 1);
                    int nextJ = l;
                    int cand = dp[i][j] + distBetween(j, l);
                    if (cand < dp[nextI][nextJ]) dp[nextI][nextJ] = cand;
                }
            }
        }

        // answer is minimum over skipping up to K checkpoints
        int ans = INF;
        for (int s = 0; s <= k; s++) ans = Math.min(ans, dp[s][n - 1]);

        pw.println(ans);
        pw.close();
        br.close();
    }

    static int distBetween(int i, int j) {
        return Math.abs(x[i] - x[j]) + Math.abs(y[i] - y[j]);
    }
}