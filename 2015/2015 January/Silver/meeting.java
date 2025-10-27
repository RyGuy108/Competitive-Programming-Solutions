import java.io.*;
import java.util.*;

public class meeting {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("meeting.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("meeting.out")));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // adjacency matrices (only store forward edges since A < B)
        int[][] bessie = new int[n][n];
        int[][] elsie = new int[n][n];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()); // Bessie's time
            int d = Integer.parseInt(st.nextToken()); // Elsie's time
            bessie[a][b] = c;
            elsie[a][b] = d;
        }

        int maxTime = 100 * (n - 1); // upper bound on time

        boolean[] bessieTimes = computeReachableTimes(bessie, n, maxTime);
        boolean[] elsieTimes  = computeReachableTimes(elsie,  n, maxTime);

        int answer = -1;
        for (int t = 0; t <= maxTime; t++) {
            if (bessieTimes[t] && elsieTimes[t]) {
                answer = t;
                break;
            }
        }

        if (answer == -1) {
            pw.println("IMPOSSIBLE");
        } else {
            pw.println(answer);
        }

        pw.close();
        br.close();
    }

    // returns boolean array times such that times[t] == true iff destination (node n-1)
    // is reachable at exact time t
    private static boolean[] computeReachableTimes(int[][] grid, int n, int maxTime) {
        boolean[][] dp = new boolean[n][maxTime + 1];
        dp[0][0] = true;

        for (int u = 0; u < n; u++) {
            for (int t = 0; t <= maxTime; t++) {
                if (!dp[u][t]) continue;
                // relax all forward edges u -> v (v > u)
                for (int v = u + 1; v < n; v++) {
                    int cost = grid[u][v];
                    if (cost > 0) {
                        int nt = t + cost;
                        if (nt <= maxTime) dp[v][nt] = true;
                    }
                }
            }
        }
        return dp[n - 1];
    }
}