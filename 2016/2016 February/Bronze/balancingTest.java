import java.io.*;
import java.util.*;

public class balancingTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("balancing.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("balancing.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken()); // unused except as problem bound

        int[] xs = new int[N];
        int[] ys = new int[N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            xs[i] = Integer.parseInt(st.nextToken());
            ys[i] = Integer.parseInt(st.nextToken());
        }

        // Candidate fence coordinates: x_i + 1 and y_j + 1
        int best = N; // worst-case: all cows in one quadrant
        for (int i = 0; i < N; i++) {
            int a = xs[i] + 1;
            for (int j = 0; j < N; j++) {
                int b = ys[j] + 1;

                int q1 = 0, q2 = 0, q3 = 0, q4 = 0;
                // classify each cow relative to vertical line x=a and horizontal y=b
                for (int k = 0; k < N; k++) {
                    if (xs[k] > a && ys[k] > b) q1++;       // top-right
                    else if (xs[k] < a && ys[k] > b) q2++;  // top-left
                    else if (xs[k] < a && ys[k] < b) q3++;  // bottom-left
                    else if (xs[k] > a && ys[k] < b) q4++;  // bottom-right
                    // cows cannot be exactly on fence because a and b are xi+1 / yi+1
                }

                int curMax = Math.max(Math.max(q1, q2), Math.max(q3, q4));
                if (curMax < best) best = curMax;
            }
        }

        pw.println(best);
        pw.close();
        br.close();
    }
}