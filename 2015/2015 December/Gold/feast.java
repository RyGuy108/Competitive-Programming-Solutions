import java.io.*;
import java.util.*;

public class feast {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("feast.in"));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int T = Integer.parseInt(st.nextToken());
        int A = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());

        boolean[][] seen = new boolean[2][T + 1];
        seen[0][0] = true;

        // a = 0 (before drinking), a = 1 (after drinking)
        for (int a = 0; a < 2; a++) {
            for (int f = 0; f <= T; f++) {
                if (!seen[a][f]) continue;
                // eat orange (A)
                if (f + A <= T) seen[a][f + A] = true;
                // eat lemon (B)
                if (f + B <= T) seen[a][f + B] = true;
                // drink water (only allowed once: move from a=0 -> a=1)
                if (a == 0) {
                    int half = f / 2;
                    seen[1][half] = true;
                }
            }
        }

        // find maximum fullness reachable after drinking (seen[1])
        int ans = 0;
        for (int f = T; f >= 0; f--) {
            if (seen[1][f]) { ans = f; break; }
        }

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("feast.out")));
        pw.println(ans);
        pw.close();
        br.close();
    }
}