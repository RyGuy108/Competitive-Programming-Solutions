import java.io.*;
import java.util.*;

public class billboard {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("billboard.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("billboard.out")));

        int[] b1 = read4(br);
        int[] b2 = read4(br);
        int[] t  = read4(br);

        long area1 = rectArea(b1);
        long area2 = rectArea(b2);

        long inter1 = intersectionArea(b1, t);
        long inter2 = intersectionArea(b2, t);

        long visible = (area1 - inter1) + (area2 - inter2);
        pw.println(visible);

        pw.close();
        br.close();
    }

    // read a line of four integers (may be on same or separate lines)
    static int[] read4(BufferedReader br) throws IOException {
        StringTokenizer st = null;
        while (st == null || st.countTokens() < 4) {
            String line = br.readLine();
            if (line == null) break;
            if (st == null) st = new StringTokenizer(line);
            else {
                // append tokens from this line
                StringTokenizer extra = new StringTokenizer(line);
                StringBuilder sb = new StringBuilder();
                while (st.hasMoreTokens()) sb.append(st.nextToken()).append(' ');
                while (extra.hasMoreTokens()) sb.append(extra.nextToken()).append(' ');
                st = new StringTokenizer(sb.toString().trim());
            }
        }
        int[] a = new int[4];
        for (int i = 0; i < 4; i++) a[i] = Integer.parseInt(st.nextToken());
        return a;
    }

    static long rectArea(int[] r) {
        // r = {x1, y1, x2, y2} (lower-left, upper-right)
        return Math.max(0, r[2] - r[0]) * 1L * Math.max(0, r[3] - r[1]);
    }

    static long intersectionArea(int[] a, int[] b) {
        int xOverlap = Math.max(0, Math.min(a[2], b[2]) - Math.max(a[0], b[0]));
        int yOverlap = Math.max(0, Math.min(a[3], b[3]) - Math.max(a[1], b[1]));
        return xOverlap * 1L * yOverlap;
    }
}