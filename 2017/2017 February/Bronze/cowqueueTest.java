import java.io.*;
import java.util.*;

public class cowqueueTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("cowqueue.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cowqueue.out")));

        int N = Integer.parseInt(br.readLine().trim());
        long current = 0; // time when the server will be free

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            long arrive = Long.parseLong(st.nextToken());
            long dur = Long.parseLong(st.nextToken());

            // start when both cow has arrived and server is free
            long start = Math.max(arrive, current);
            current = start + dur;
        }

        pw.println(current);
        pw.close();
        br.close();
    }
}