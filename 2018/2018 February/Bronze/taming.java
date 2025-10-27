import java.io.*;
import java.util.*;

public class taming {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("taming.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("taming.out")));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine().trim());
        int[] A = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }
        br.close();

        // day 0 must be a breakout
        if (A[0] > 0) {
            pw.println(-1);
            pw.close();
            return;
        }
        if (A[0] == -1) A[0] = 0;

        // enforce constraints for each known entry
        for (int i = 0; i < N; i++) {
            if (A[i] >= 0) {
                int start = i - A[i];
                if (start < 0) { // breakout would be before day 0 -> impossible
                    pw.println(-1);
                    pw.close();
                    return;
                }
                for (int t = 0; t <= A[i]; t++) {
                    int idx = start + t;
                    if (A[idx] == -1) {
                        A[idx] = t;
                    } else if (A[idx] != t) {
                        pw.println(-1);
                        pw.close();
                        return;
                    }
                }
            }
        }

        // count minimum (number of zeros forced) and remaining unknowns (for max)
        int minBreakouts = 0;
        int unknowns = 0;
        for (int i = 0; i < N; i++) {
            if (A[i] == 0) minBreakouts++;
            else if (A[i] == -1) unknowns++;
        }

        pw.println(minBreakouts + " " + (minBreakouts + unknowns));
        pw.close();
    }
}