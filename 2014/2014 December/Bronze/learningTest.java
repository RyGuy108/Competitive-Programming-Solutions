import java.io.*;
import java.util.*;

public class learningTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("learning.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("learning.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        long A = Long.parseLong(st.nextToken());
        long B = Long.parseLong(st.nextToken());

        // Read cows
        long[] w = new long[N];
        boolean[] spot = new boolean[N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            String t = st.nextToken();         // "S" or "NS"
            long weight = Long.parseLong(st.nextToken());
            w[i] = weight;
            spot[i] = t.equals("S");
        }

        // Sort by weight (weights are distinct)
        Integer[] idx = new Integer[N];
        for (int i = 0; i < N; i++) idx[i] = i;
        Arrays.sort(idx, (a, b) -> Long.compare(w[a], w[b]));
        long[] ws = new long[N];
        boolean[] sspot = new boolean[N];
        for (int i = 0; i < N; i++) {
            ws[i] = w[idx[i]];
            sspot[i] = spot[idx[i]];
        }

        // compute boundaries b_i = floor((w_i + w_{i+1})/2)
        long[] b = new long[Math.max(0, N - 1)];
        for (int i = 0; i < N - 1; i++) {
            b[i] = (ws[i] + ws[i + 1]) / 2L; // floor division
        }

        long ans = 0L;

        // First cow region: (-inf .. b[0]]
        if (N >= 1) {
            long high = (N > 1) ? b[0] : Long.MAX_VALUE / 4;
            long L = A;
            long R = Math.min(B, high);
            if (R >= L && sspot[0]) ans += (R - L + 1);
        }

        // Middle cows
        for (int i = 1; i <= N - 2; i++) {
            long low = b[i - 1] + 1;
            long high = b[i];
            long L = Math.max(A, low);
            long R = Math.min(B, high);
            if (R >= L && sspot[i]) ans += (R - L + 1);
        }

        // Last cow region: [b[N-2]+1 .. +inf)
        if (N >= 2) {
            long low = b[N - 2] + 1;
            long L = Math.max(A, low);
            long R = B;
            if (R >= L && sspot[N - 1]) ans += (R - L + 1);
        }

        // Handle tie midpoints exactly (where (w_i + w_{i+1}) is even).
        // If midpoint is in [A,B] and left is NOT spotted and right IS spotted,
        // then that integer was not counted above (it was assigned to left region),
        // but by tie rule it should be classified spotted -> add one.
        for (int i = 0; i < N - 1; i++) {
            long sum = ws[i] + ws[i + 1];
            if ((sum & 1L) == 0L) { // even sum -> integer midpoint exists
                long mid = sum / 2L;
                if (mid >= A && mid <= B) {
                    if (!sspot[i] && sspot[i + 1]) ans++;
                }
            }
        }

        pw.println(ans);
        pw.close();
        br.close();
    }
}