import java.io.*;
import java.util.*;

public class barnpainting {
    static final int MOD = 1000000007;
    static LinkedList<Integer>[] edges;
    static int[] color;         // -1 if not fixed, otherwise 0..2 for fixed color
    static long[][] dp;         // dp[v][c] = number of ways to color subtree rooted at v if v is color c

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("barnpainting.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("barnpainting.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        edges = new LinkedList[n];
        for (int i = 0; i < n; i++) edges[i] = new LinkedList<>();

        color = new int[n];
        Arrays.fill(color, -1);

        // read edges (tree has n-1 edges)
        for (int i = 1; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            edges[a].add(b);
            edges[b].add(a);
        }

        // read precolored barns
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int barn = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1; // convert to 0..2
            color[barn] = c;
        }

        dp = new long[n][3];
        for (int i = 0; i < n; i++) Arrays.fill(dp[i], -1L);

        // compute total ways rooting the tree at 0, summing over root colors
        long ans = (solve(0, 0, -1) + solve(0, 1, -1)) % MOD;
        ans = (ans + solve(0, 2, -1)) % MOD;

        pw.println(ans);
        pw.close();
    }

    // returns number of ways to color subtree rooted at curr with color currC,
    // given parent is par. Colors 0..2. If curr is precolored and not currC, return 0.
    public static long solve(int curr, int currC, int par) {
        // invalid if curr has fixed color different than currC
        if (color[curr] >= 0 && color[curr] != currC) return 0;
        if (dp[curr][currC] != -1) return dp[curr][currC];

        long ways = 1L;
        // multiply possibilities of each child
        for (int v : edges[curr]) {
            if (v == par) continue;
            long sumForChild = 0L;
            // child can be any color != currC
            for (int c = 0; c < 3; c++) {
                if (c == currC) continue;
                sumForChild += solve(v, c, curr);
                if (sumForChild >= MOD) sumForChild -= MOD;
            }
            ways = (ways * (sumForChild % MOD)) % MOD;
            if (ways == 0) break;
        }

        dp[curr][currC] = ways % MOD;
        return dp[curr][currC];
    }
}