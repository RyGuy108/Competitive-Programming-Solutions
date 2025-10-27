import java.io.*;
import java.util.*;

public class crossroadTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("crossroad.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("crossroad.out")));

        int N = Integer.parseInt(br.readLine().trim());
        int[] last = new int[11];          // cow IDs 1..10
        Arrays.fill(last, -1);             // -1 means "not seen yet"
        int crossings = 0;

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int id = Integer.parseInt(st.nextToken());
            int side = Integer.parseInt(st.nextToken());
            if (last[id] != -1 && last[id] != side) {
                crossings++;
            }
            last[id] = side;
        }

        pw.println(crossings);
        pw.close();
        br.close();
    }
}