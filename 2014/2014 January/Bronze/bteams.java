import java.io.*;
import java.util.*;

/**
 * Balanced teams (12 cows into 4 teams of 3) - brute force via 2-bit encoding per cow.
 * We fix cow 11 to team 0 to cut the search space by factor 4 (like the C++ solution).
 *
 * For mask x in [0 .. (1<<22)-1] we interpret 11 cows (indices 0..10) each with 2 bits:
 *   team = (x >> (2*i)) & 3
 * cow 11 is fixed to team 0 and its skill is pre-added to total[0], count[0] = 1.
 *
 * We prune immediately if any team's count exceeds 3.
 */
public class bteams {
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);

        int[] skill = new int[12];
        for (int i = 0; i < 12; i++) {
            skill[i] = fs.nextInt();
        }

        int best = Integer.MAX_VALUE;

        // We'll fix cow index 11 into team 0 (count[0] = 1, total[0] = skill[11]).
        // Enumerate assignments for cows 0..10 using 22 bits (2 bits each)
        final int LIMIT = 1 << 22; // 4,194,304

        for (int x = 0; x < LIMIT; x++) {
            int[] count = new int[4];
            int[] total = new int[4];

            count[0] = 1;
            total[0] = skill[11];

            boolean bad = false;

            // assign cows 0..10
            for (int i = 0; i < 11; i++) {
                int team = (x >> (2 * i)) & 3;
                count[team]++;
                if (count[team] > 3) { // pruning
                    bad = true;
                    break;
                }
                total[team] += skill[i];
            }
            if (bad) continue;

            // ensure every team has exactly 3 cows
            if (count[0] != 3 || count[1] != 3 || count[2] != 3 || count[3] != 3) continue;

            int mn = Integer.MAX_VALUE, mx = Integer.MIN_VALUE;
            for (int t = 0; t < 4; t++) {
                if (total[t] < mn) mn = total[t];
                if (total[t] > mx) mx = total[t];
            }
            int diff = mx - mn;
            if (diff < best) best = diff;
        }

        System.out.println(best);
    }

    // Minimal fast scanner
    static class FastScanner {
        private final InputStream in;
        private final byte[] buf = new byte[1 << 16];
        private int ptr = 0, len = 0;
        FastScanner(InputStream is) { in = is; }
        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buf);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buf[ptr++];
        }
        public int nextInt() throws IOException {
            int c;
            while ((c = read()) <= ' ') if (c == -1) return Integer.MIN_VALUE;
            int sign = 1;
            if (c == '-') { sign = -1; c = read(); }
            int val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sign;
        }
    }
}