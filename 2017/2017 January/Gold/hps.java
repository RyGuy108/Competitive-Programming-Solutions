import java.io.*;
import java.util.*;

public class hps {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("hps.in"));
        StringTokenizer st = new StringTokenizer(br.readLine().trim());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        char[] moves = new char[N];
        for (int i = 0; i < N; i++) {
            moves[i] = br.readLine().trim().charAt(0);
        }

        // dp[i][j][s] = max wins after i games, using j switches, ending with gesture s
        // s: 0=H, 1=P, 2=S
        int[][][] dp = new int[N + 1][K + 1][3];

        for (int i = 1; i <= N; i++) {
            for (int j = 0; j <= K; j++) {
                for (int s = 0; s < 3; s++) {
                    int winHere = wins(s, moves[i - 1]) ? 1 : 0;
                    if (j == 0) {
                        // cannot switch, must keep same gesture s as previous
                        dp[i][j][s] = dp[i - 1][j][s] + winHere;
                    } else {
                        // either stayed with s, or switched from one of the other two gestures
                        int o1 = (s + 1) % 3;
                        int o2 = (s + 2) % 3;
                        int bestPrev = Math.max(dp[i - 1][j][s], Math.max(dp[i - 1][j - 1][o1], dp[i - 1][j - 1][o2]));
                        dp[i][j][s] = bestPrev + winHere;
                    }
                }
            }
        }

        int ans = Math.max(dp[N][K][0], Math.max(dp[N][K][1], dp[N][K][2]));

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("hps.out")));
        pw.println(ans);
        pw.close();
        br.close();
    }

    private static boolean wins(int state, char fj) {
        // state 0=H,1=P,2=S
        // H beats S, P beats H, S beats P
        if (state == 0 && fj == 'S') return true;
        if (state == 1 && fj == 'H') return true;
        if (state == 2 && fj == 'P') return true;
        return false;
    }
}