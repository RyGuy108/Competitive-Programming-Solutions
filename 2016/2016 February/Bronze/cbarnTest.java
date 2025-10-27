import java.io.*;
import java.util.*;

public class cbarnTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("cbarn.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cbarn.out")));

        int n = Integer.parseInt(br.readLine().trim());
        long[] r = new long[n];
        long total = 0;
        for (int i = 0; i < n; i++) {
            r[i] = Long.parseLong(br.readLine().trim());
            total += r[i];
        }

        // cost when door is at room 0 (i.e., k = 1 in 1-based)
        long cost = 0;
        for (int i = 0; i < n; i++) {
            cost += r[i] * i; // distance from door at index 0 to room i is i
        }

        long best = cost;
        // rotate door: formula cost_next = cost - total + n * r[k]
        for (int k = 0; k < n - 1; k++) {
            cost = cost - total + n * r[k];
            if (cost < best) best = cost;
        }

        pw.println(best);
        pw.close();
        br.close();
    }
}