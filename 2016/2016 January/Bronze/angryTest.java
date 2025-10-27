import java.io.*;
import java.util.*;

public class angryTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("angry.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("angry.out")));

        int N = Integer.parseInt(br.readLine().trim());
        long[] x = new long[N];
        for (int i = 0; i < N; i++) {
            x[i] = Long.parseLong(br.readLine().trim());
        }
        Arrays.sort(x);

        int best = 1;

        // For each starting bale index i, expand left and right greedily.
        for (int i = 0; i < N; i++) {
            // expand left
            int L = i;
            long radius = 1;
            while (true) {
                int newL = L;
                // move newL left while the next left bale is within current radius
                while (newL - 1 >= 0 && x[L] - x[newL - 1] <= radius) {
                    newL--;
                }
                if (newL == L) break;
                L = newL;
                radius++;
            }

            // expand right
            int R = i;
            radius = 1;
            while (true) {
                int newR = R;
                // move newR right while the next right bale is within current radius
                while (newR + 1 < N && x[newR + 1] - x[R] <= radius) {
                    newR++;
                }
                if (newR == R) break;
                R = newR;
                radius++;
            }

            best = Math.max(best, R - L + 1);
        }

        pw.println(best);
        pw.close();
        br.close();
    }
}