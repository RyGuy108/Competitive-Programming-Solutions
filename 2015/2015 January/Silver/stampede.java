import java.io.*;
import java.util.*;

/**
 * Java solution for USACO "Stampede" following the provided C++ template.
 */
public class stampede {
    static class Cow {
        int y;
        int start; // inclusive start of integer-scaled interval
        int end;   // exclusive end (we will use [start, end) as in C++ code)
        Cow(int y, int start, int end) { this.y = y; this.start = start; this.end = end; }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("stampede.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("stampede.out")));

        int N = Integer.parseInt(br.readLine().trim());
        ArrayList<Cow> cows = new ArrayList<>(N);
        TreeSet<Integer> times = new TreeSet<>();

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()); // negative
            int y = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());

            // scale as in C++: x' = -r * x, interval [x' - r, x']
            int xp = -r * x;                 // safe in int given constraints
            int start = xp - r;              // corresponds to r*(-x-1)
            int end = xp;                    // corresponds to r*(-x)

            cows.add(new Cow(y, start, end));

            // insert neighboring integers as in C++ to cover boundary cases
            times.add(start - 1);
            times.add(start);
            times.add(start + 1);
            times.add(end - 1);
            times.add(end);
            times.add(end + 1);
        }

        // sort cows by y ascending
        cows.sort(Comparator.comparingInt(c -> c.y));

        int result = 0;
        // For each cow, check if there's any integer in [start, end)
        // If so, count cow and erase all integers in that half-open range
        for (Cow c : cows) {
            Integer cur = times.ceiling(c.start);
            if (cur != null && cur < c.end) {
                // There is at least one integer in [start, end)
                result++;
                // Remove all integers in [start, end) (each integer removed at most once overall)
                while (true) {
                    Integer toRemove = times.ceiling(c.start);
                    if (toRemove == null || toRemove >= c.end) break;
                    times.remove(toRemove);
                }
            }
        }

        pw.println(result);
        pw.close();
        br.close();
    }
}