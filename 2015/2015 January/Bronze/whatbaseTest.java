import java.io.*;
import java.util.*;

public class whatbaseTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("whatbase.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("whatbase.out")));
        int K = Integer.parseInt(br.readLine().trim());
        final int MINB = 10;
        final int MAXB = 15000;

        for (int tc = 0; tc < K; tc++) {
            String line = br.readLine().trim();
            while (line.isEmpty()) line = br.readLine().trim();
            String[] parts = line.split("\\s+");
            String s1 = parts[0];
            String s2 = parts[1];

            int a = s1.charAt(0) - '0';
            int b = s1.charAt(1) - '0';
            int c = s1.charAt(2) - '0';

            int d = s2.charAt(0) - '0';
            int e = s2.charAt(1) - '0';
            int f = s2.charAt(2) - '0';

            // map N -> base X for the first representation
            HashMap<Long, Integer> map = new HashMap<>(MAXB - MINB + 1);
            for (int X = MINB; X <= MAXB; X++) {
                long N = (long)a * X * X + (long)b * X + c;
                map.put(N, X);
            }

            boolean found = false;
            for (int Y = MINB; Y <= MAXB && !found; Y++) {
                long N2 = (long)d * Y * Y + (long)e * Y + f;
                Integer X = map.get(N2);
                if (X != null) {
                    pw.println(X + " " + Y);
                    found = true;
                }
            }

            // problem guarantees a unique solution, so found should be true
        }

        pw.close();
        br.close();
    }
}