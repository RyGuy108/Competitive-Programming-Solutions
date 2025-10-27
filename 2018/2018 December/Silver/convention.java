import java.io.*;
import java.util.*;

public class convention {
    static int N, M, C;
    static int[] t;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("convention.in"));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        t = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            t[i] = Integer.parseInt(st.nextToken());
        }
        br.close();

        Arrays.sort(t);

        int low = 0;
        int high = 1_000_000_000; // arrival times up to 1e9, so this is a safe upper bound

        // Binary search for smallest wait such that pos(wait) is true
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (pos(mid)) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        PrintWriter out = new PrintWriter(new FileWriter("convention.out"));
        out.println(low);
        out.close();
    }

    /**
     * Greedy feasibility: can we schedule all cows into at most M buses
     * if no one waits more than `wait`?
     *
     * We walk through sorted arrival times. Start a new bus at the current
     * cow, then pack up to C cows while their arrival difference from the
     * first in this bus <= wait. Repeat and count buses used.
     */
    static boolean pos(int wait) {
        int buses = 1;
        int firstArrival = t[0];
        int firstIndex = 0; // index of first cow assigned to current bus

        for (int i = 1; i < N; i++) {
            // If adding this cow would exceed capacity or the wait constraint,
            // start a new bus beginning at this cow.
            if (t[i] - firstArrival > wait || (i - firstIndex + 1) > C) {
                buses++;
                firstArrival = t[i];
                firstIndex = i;
            }
        }
        return buses <= M;
    }
}