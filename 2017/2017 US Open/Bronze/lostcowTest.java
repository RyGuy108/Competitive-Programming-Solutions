import java.io.*;
import java.util.*;

public class lostcowTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("lostcow.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("lostcow.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        if (x == y) {
            pw.println(0);
            pw.close();
            br.close();
            return;
        }

        long total = 0;
        int cur = x;
        int step = 1;      // magnitude 2^(k-1)
        int dir = 1;       // +1 then -1 then +1 ...
        while (true) {
            int target = x + dir * step;
            total += Math.abs(target - cur);

            // if y lies on the segment from cur to target (inclusive), we find Bessie here
            if ((cur <= y && y <= target) || (target <= y && y <= cur)) {
                // we added the whole leg; subtract the overshoot past y
                total -= Math.abs(target - y);
                break;
            }

            // otherwise continue from target
            cur = target;
            dir = -dir;
            step *= 2;
        }

        pw.println(total);
        pw.close();
        br.close();
    }
}