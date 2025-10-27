import java.io.*;
import java.util.*;

public class cowdance {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("cowdance.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cowdance.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int maxT = Integer.parseInt(st.nextToken());
        int[] l = new int[n];
        for (int i = 0; i < n; i++) {
            l[i] = Integer.parseInt(br.readLine().trim());
        }

        int lo = 1, hi = n;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (possible(l, mid, maxT)) hi = mid;
            else lo = mid + 1;
        }
        pw.println(lo);
        pw.close();
        br.close();
    }

    // return true if using k simultaneous dancers the show finishes in <= t
    public static boolean possible(int[] l, int k, int t) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int lastStart = 0;
        for (int i = 0; i < l.length; i++) {
            // if all k dancers are busy, wait for the earliest one to finish
            if (pq.size() == k) {
                lastStart = pq.poll();
            } else {
                // when there are free dancers we start at time 0 for initial ones
                lastStart = 0;
            }
            int finish = lastStart + l[i];
            if (finish > t) return false;
            pq.add(finish);
        }
        return true;
    }
}