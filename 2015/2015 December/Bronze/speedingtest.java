import java.io.*;
import java.util.*;

public class speedingtest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("speeding.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("speeding.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken()); // number of road segments
        int M = Integer.parseInt(st.nextToken()); // number of Bessie's segments

        // The road is exactly 100 miles long; index 0..99 correspond to mile positions.
        int[] limit = new int[100];
        int pos = 0;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int len = Integer.parseInt(st.nextToken());
            int spd = Integer.parseInt(st.nextToken());
            for (int j = 0; j < len; j++) {
                limit[pos++] = spd;
            }
        }

        int[] bessie = new int[100];
        pos = 0;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int len = Integer.parseInt(st.nextToken());
            int spd = Integer.parseInt(st.nextToken());
            for (int j = 0; j < len; j++) {
                bessie[pos++] = spd;
            }
        }

        int maxOver = 0;
        for (int i = 0; i < 100; i++) {
            int over = bessie[i] - limit[i];
            if (over > maxOver) maxOver = over;
        }

        pw.println(maxOver);
        pw.close();
        br.close();
    }
}
