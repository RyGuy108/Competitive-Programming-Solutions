import java.io.*;
import java.util.*;

/*
 * angryTest.java
 * Binary search + greedy for "Angry" (minimum R so K blasts of radius R cover all hay bales).
 *
 * Reads:
 *   n k
 *   x1
 *   x2
 *   ...
 *
 * Writes:
 *   minimum integer R
 */
public class angryTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("angry.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("angry.out")));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        long[] loc = new long[n];
        for (int i = 0; i < n; i++) {
            // skip blank lines if any
            String line = br.readLine();
            while (line != null && line.trim().isEmpty()) line = br.readLine();
            loc[i] = Long.parseLong(line.trim());
        }
        Arrays.sort(loc);

        long lo = 0;
        long hi = (long)1e9; // upper bound on R (positions <= 1e9)
        while (lo < hi) {
            long mid = lo + (hi - lo) / 2;
            if (canCoverWithK(loc, n, k, mid)) hi = mid;
            else lo = mid + 1;
        }

        pw.println(lo);
        pw.close();
        br.close();
    }

    // Return true if radius R is sufficient using at most K cows
    static boolean canCoverWithK(long[] loc, int n, int K, long R) {
        int used = 0;
        int i = 0;
        long coverLen = 2L * R;
        while (i < n && used <= K) {
            used++;
            long start = loc[i];
            // greedily include as many hay bales as lie within [start, start + 2R]
            int j = i + 1;
            while (j < n && loc[j] - start <= coverLen) j++;
            i = j; // next uncovered bale
        }
        return used <= K;
    }
}