import java.io.*;
import java.util.*;

public class mowingTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("mowing.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("mowing.out")));
        int N = Integer.parseInt(br.readLine().trim());

        // Map a coordinate pair to the last time it was visited
        Map<Long, Integer> lastVisit = new HashMap<>();

        // Start at (0,0) at time 0
        int x = 0, y = 0, time = 0;
        lastVisit.put(pack(x, y), time);

        int best = Integer.MAX_VALUE; // minimum revisit interval found

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            char dir = st.nextToken().charAt(0);
            int steps = Integer.parseInt(st.nextToken());
            for (int s = 0; s < steps; s++) {
                time++;
                if (dir == 'N') y++;
                else if (dir == 'S') y--;
                else if (dir == 'E') x++;
                else if (dir == 'W') x--;

                long key = pack(x, y);
                if (lastVisit.containsKey(key)) {
                    int diff = time - lastVisit.get(key);
                    if (diff < best) best = diff;
                }
                lastVisit.put(key, time);
            }
        }

        pw.println(best == Integer.MAX_VALUE ? -1 : best);
        pw.close();
        br.close();
    }

    // pack two ints (x, y) into a single long key
    private static long pack(int x, int y) {
        return (((long) x) << 32) ^ (y & 0xffffffffL);
    }
}