import java.io.*;
import java.util.*;

public class cowjog {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("cowjog.in"));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        long T = Long.parseLong(st.nextToken());

        long[] end = new long[N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            long x = Long.parseLong(st.nextToken());
            long s = Long.parseLong(st.nextToken());
            end[i] = x + s * T; // safe in long
        }
        br.close();

        int groups = 0;
        long minSoFar = Long.MAX_VALUE;
        for (int i = N - 1; i >= 0; i--) {
            if (end[i] < minSoFar) {
                groups++;
                minSoFar = end[i];
            }
        }

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cowjog.out")));
        pw.println(groups);
        pw.close();
    }
}