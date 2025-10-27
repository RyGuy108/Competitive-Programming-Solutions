import java.io.*;
import java.util.*;

public class cowjogTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("cowjog.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cowjog.out")));

        int N = Integer.parseInt(br.readLine().trim());
        long[] pos = new long[N];
        long[] speed = new long[N];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            pos[i] = Long.parseLong(st.nextToken());
            speed[i] = Long.parseLong(st.nextToken());
        }

        int groups = 0;
        long minSpeed = Long.MAX_VALUE; // min speed of groups seen so far (from the right)
        for (int i = N - 1; i >= 0; i--) {
            if (speed[i] <= minSpeed) {
                groups++;
                minSpeed = speed[i];
            }
            // else: speed[i] > minSpeed -> this cow will catch up to the group ahead
        }

        pw.println(groups);
        pw.close();
        br.close();
    }
}