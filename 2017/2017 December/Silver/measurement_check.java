import java.io.*;
import java.util.*;

public class measurement_check {
    static class Meas {
        int day;
        int cow;
        int delta;
        Meas(int d, int c, int dv) { day = d; cow = c; delta = dv; }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("measurement.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("measurement.out")));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int G = Integer.parseInt(st.nextToken());

        ArrayList<Meas> A = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int day = Integer.parseInt(st.nextToken());
            int cow = Integer.parseInt(st.nextToken());
            int delta = Integer.parseInt(st.nextToken());
            A.add(new Meas(day, cow, delta));
        }
        A.sort(Comparator.comparingInt(m -> m.day));

        // counts: map from value -> number of cows that currently have that "value"
        // use descending comparator so firstKey() is the current maximum value
        TreeMap<Integer, Integer> cnts = new TreeMap<>(Comparator.reverseOrder());
        // Mark's trick: store 0 for all cows initially; put N+1 so that "unseen" cows (G) dominate
        cnts.put(0, N + 1);

        int result = 0;
        // mp: cow -> its current value (value = currentProduction - G). Initially 0 for all cows
        HashMap<Integer, Integer> mp = new HashMap<>();

        for (Meas m : A) {
            int cow = m.cow;
            int delta = m.delta;

            int ref = mp.getOrDefault(cow, 0);

            // was the cow at the top before this measurement?
            int currentTop = cnts.firstKey(); // largest key due to reverseOrder
            boolean wasTop = ref == currentTop;

            // decrement count of old value
            int oldCnt = cnts.getOrDefault(ref, 0);
            if (oldCnt <= 1) cnts.remove(ref);
            else cnts.put(ref, oldCnt - 1);

            // update cow's stored relative value
            ref += delta;
            mp.put(cow, ref);

            // increment count of new value
            int newCnt = cnts.getOrDefault(ref, 0) + 1;
            cnts.put(ref, newCnt);

            // recompute top
            int newTop = cnts.firstKey();
            boolean isTop = ref == newTop;

            // replicate Mark's logic for counting a display change
            if (wasTop) {
                // if cow used to be top but is not top now, or if counts changed such that
                // uniqueness changed, it's a display change
                if (!isTop || oldCnt != 1 || newCnt != 1) {
                    result++;
                }
            } else if (isTop) {
                // cow became (one of) top cow(s)
                result++;
            }
        }

        pw.println(result);
        pw.close();
        br.close();
    }
}