import java.io.*;
import java.util.*;

public class skidesign {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("skidesign.in"));
        PrintWriter out = new PrintWriter(new FileWriter("skidesign.out"));

        int n = Integer.parseInt(br.readLine().trim());
        int[] hills = new int[n];
        for (int i = 0; i < n; i++) {
            hills[i] = Integer.parseInt(br.readLine().trim());
        }
        br.close();

        int minCost = Integer.MAX_VALUE;

        // Try every 17-wide interval [low, low+17]. low ranges 0..83 (since heights ≤ 100)
        for (int low = 0; low <= 83; low++) {
            int high = low + 17;
            int cost = 0;
            for (int h : hills) {
                if (h < low) {
                    int d = low - h;
                    cost += d * d;
                } else if (h > high) {
                    int d = h - high;
                    cost += d * d;
                }
            }
            if (cost < minCost) minCost = cost;
        }

        out.println(minCost);
        out.close();
    }
}