import java.io.*;
import java.util.*;

public class angry {
    public static ArrayList<Long> l = new ArrayList<>();
    public static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader f = new BufferedReader(new FileReader("angry.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("angry.out")));

        N = Integer.parseInt(f.readLine().trim());
        for (int i = 0; i < N; i++) {
            l.add(Long.parseLong(f.readLine().trim()));
        }
        Collections.sort(l);

        // trivial cases
        if (N == 0) {
            out.printf("%.1f\n", 0.0);
            out.close();
            return;
        }
        if (N == 1) {
            out.printf("%.1f\n", 0.0);
            out.close();
            return;
        }

        int beg = 0;
        long rad = 0;
        int end = N - 1;
        long rad1 = 0;
        double best = Double.MAX_VALUE;

        while (true) {
            // current candidate value
            double gapHalf = (l.get(end) - l.get(beg)) / 2.0;
            double val = Math.max((double)Math.max(rad, rad1) + 1.0, gapHalf);
            if (val < best) best = val;

            // potential next radii if we extend from left or right
            long pot = Math.max(rad + 1, l.get(beg + 1) - l.get(beg));
            long pot1 = Math.max(rad1 + 1, l.get(end) - l.get(end - 1));

            if (pot < pot1) {
                rad = pot;
                int index = beg;
                // move beg as far right as possible while covered by radius 'pot'
                while (index < N - 1 && l.get(beg) + pot >= l.get(index + 1)) {
                    index++;
                }
                beg = index;
            } else {
                rad1 = pot1;
                int index = end;
                // move end as far left as possible while covered by radius 'pot1'
                while (index > 0 && l.get(end) - pot1 <= l.get(index - 1)) {
                    index--;
                }
                end = index;
            }

            if (end <= beg) {
                best = Math.min(best, (double)Math.max(rad, rad1));
                break;
            }
        }

        out.printf("%.1f\n", best);
        out.close();
    }
}