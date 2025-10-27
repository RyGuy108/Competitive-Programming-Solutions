import java.io.*;
import java.util.*;

public class reduce {
    // x1,x2 = smallest & 2nd-smallest x
    // x3,x4 = 2nd-largest & largest x
    static int x1, x2, x3, x4;
    // y1,y2 = smallest & 2nd-smallest y
    // y3,y4 = 2nd-largest & largest y
    static int y1, y2, y3, y4;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("reduce.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("reduce.out")));

        int N = Integer.parseInt(br.readLine());
        int[] xs = new int[N], ys = new int[N];

        // Initialize extremes
        x1 = y1 = Integer.MAX_VALUE;
        x2 = y2 = Integer.MAX_VALUE;
        x3 = y3 = Integer.MIN_VALUE;
        x4 = y4 = Integer.MIN_VALUE;

        // Read points and update the four extremes in one pass
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            xs[i] = x;
            ys[i] = y;
            updateExtremes(x, y);
        }

        long best = Long.MAX_VALUE;

        // Only cows on one of the extremes can change the area if removed
        for (int i = 0; i < N; i++) {
            int cx = xs[i], cy = ys[i];
            int minX = (cx == x1 ? x2 : x1);
            int maxX = (cx == x4 ? x3 : x4);
            int minY = (cy == y1 ? y2 : y1);
            int maxY = (cy == y4 ? y3 : y4);
            long area = (long)(maxX - minX) * (maxY - minY);
            best = Math.min(best, area);
        }

        pw.println(best);
        pw.close();
        br.close();
    }

    private static void updateExtremes(int x, int y) {
        // update smallest two x's
        if (x < x1) {
            x2 = x1; x1 = x;
        } else if (x < x2) { // x1 < x < x2
            x2 = x;
        }
        // update largest two x's
        if (x > x4) {
            x3 = x4; x4 = x;
        } else if (x > x3) { // x4 > x > x3
            x3 = x;
        }

        // update smallest two y's
        if (y < y1) {
            y2 = y1; y1 = y;
        } else if (y < y2) {
            y2 = y;
        }
        // update largest two y's
        if (y > y4) {
            y3 = y4; y4 = y;
        } else if (y > y3) {
            y3 = y;
        }
    }
}