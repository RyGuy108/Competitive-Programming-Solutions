import java.io.*;
import java.util.*;

public class cowrouteTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("cowroute.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cowroute.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int A = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());

        int best = Integer.MAX_VALUE;

        for (int r = 0; r < N; r++) {
            st = new StringTokenizer(br.readLine());
            int cost = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            int posA = -1, posB = -1;
            for (int i = 0; i < m; i++) {
                int city = Integer.parseInt(st.nextToken());
                if (city == A && posA == -1) posA = i;
                if (city == B && posB == -1) posB = i;
            }

            if (posA != -1 && posB != -1 && posA < posB) {
                best = Math.min(best, cost);
            }
        }

        pw.println(best == Integer.MAX_VALUE ? -1 : best);
        pw.close();
        br.close();
    }
}