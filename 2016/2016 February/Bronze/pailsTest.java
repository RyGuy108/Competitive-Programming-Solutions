import java.io.*;
import java.util.*;

public class pailsTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("pails.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("pails.out")));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int X = Integer.parseInt(st.nextToken());
        int Y = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int best = 0;
        // Try using a copies of X (0..M/X). For each, add as many Y's as possible.
        for (int a = 0; a * X <= M; a++) {
            int used = a * X;
            int b = (M - used) / Y;          // max number of Y pails we can add without overflow
            int total = used + b * Y;
            if (total > best) best = total;
        }

        pw.println(best);
        pw.close();
        br.close();
    }
}