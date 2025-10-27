import java.io.*;
import java.util.*;

public class div7Test {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("div7.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("div7.out")));

        int N = Integer.parseInt(br.readLine().trim());
        // first[r] = earliest index where prefix sum %7 == r
        // last[r]  = latest index where prefix sum %7 == r
        int[] first = new int[7];
        int[] last  = new int[7];
        Arrays.fill(first, Integer.MAX_VALUE);
        Arrays.fill(last, -1);

        // prefix sum at index 0 is 0 -> remainder 0 occurs at position 0
        first[0] = 0;
        last[0] = 0;

        long pref = 0L;
        for (int i = 1; i <= N; i++) {
            long val = Long.parseLong(br.readLine().trim());
            pref += val;
            int r = (int)((pref % 7 + 7) % 7); // normalized remainder 0..6
            if (i < first[r]) first[r] = i;
            last[r] = i;
        }

        int best = 0;
        for (int r = 0; r < 7; r++) {
            if (last[r] != -1 && first[r] != Integer.MAX_VALUE) {
                best = Math.max(best, last[r] - first[r]);
            }
        }

        pw.println(best);
        pw.close();
        br.close();
    }
}