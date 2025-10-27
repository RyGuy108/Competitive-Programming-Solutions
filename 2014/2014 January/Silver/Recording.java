import java.io.*;
import java.util.*;

public class Recording {
    static int n;               // original number of programs
    static int[] A;             // start times
    static int[] B;             // end times
    static int[][] memo;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("recording.in"));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        A = new int[n + 1]; // extra slot for sentinel
        B = new int[n + 1];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            A[i] = Integer.parseInt(st.nextToken());
            B[i] = Integer.parseInt(st.nextToken());
        }
        // sentinel program index = n with start=end=0
        A[n] = 0;
        B[n] = 0;

        memo = new int[n + 1][n + 1];
        for (int i = 0; i <= n; i++) Arrays.fill(memo[i], -1);

        int ans = solve(n, n); // last recorded programs are the sentinel on both tracks
        PrintWriter pw = new PrintWriter(new FileWriter("recording.out"));
        pw.println(ans);
        pw.close();
        br.close();
    }

    // returns max number of additional programs we can schedule
    // given last recorded program on track1 is x and on track2 is y.
    static int solve(int x, int y) {
        // ensure B[x] <= B[y], otherwise swap to enforce canonical ordering
        if (B[y] < B[x]) return solve(y, x);

        if (memo[x][y] != -1) return memo[x][y];

        int best = 0;
        // try to schedule any program i (0..n-1) that starts after B[x]
        // and is not equal to y (so we don't choose the same program on both tracks)
        for (int i = 0; i < n; i++) {
            if (i == y) continue;
            if (A[i] >= B[x]) {
                best = Math.max(best, 1 + solve(i, y));
            }
        }

        memo[x][y] = best;
        return best;
    }
}