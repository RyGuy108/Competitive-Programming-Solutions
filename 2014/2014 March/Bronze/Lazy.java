import java.io.*;
import java.util.*;

/**
 * Lazy Cow (Java)
 * Reads patches (g, x) from lazy.in:
 *   N K
 *   g1 x1
 *   ...
 * Computes maximum total grass reachable within distance K of some point.
 *
 * Approach:
 *  - Sort patches by x
 *  - Use two-pointer sliding window where for right index i we keep left index p
 *    such that x[i] - x[p] <= 2*K (interval length 2K)
 *  - Maintain running sum of g in window, update max
 *
 * Complexity: O(N log N) due to sorting, O(N) sliding window.
 */
public class Lazy {
    static class Patch implements Comparable<Patch> {
        int x;
        int g;
        Patch(int g, int x) { this.g = g; this.x = x; }
        public int compareTo(Patch o) { return Integer.compare(this.x, o.x); }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("lazy.in"));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        long K = Long.parseLong(st.nextToken());

        Patch[] patches = new Patch[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int g = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            patches[i] = new Patch(g, x);
        }
        br.close();

        Arrays.sort(patches);

        long best = 0;
        long sum = 0;
        int left = 0;
        long maxSpan = 2 * K; // window length allowed: difference <= 2K

        for (int right = 0; right < n; right++) {
            sum += patches[right].g;
            // Move left forward while window is too wide
            while (patches[right].x - patches[left].x > maxSpan) {
                sum -= patches[left].g;
                left++;
            }
            if (sum > best) best = sum;
        }

        PrintWriter pw = new PrintWriter(new FileWriter("lazy.out"));
        pw.println(best);
        pw.close();
    }
}