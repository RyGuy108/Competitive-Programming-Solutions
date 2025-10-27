import java.io.*;
import java.util.*;

public class outofplace {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("outofplace.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("outofplace.out")));

        int n = Integer.parseInt(br.readLine().trim());
        int[] height = new int[n];
        int[] sorted = new int[n];
        for (int i = 0; i < n; i++) {
            height[i] = Integer.parseInt(br.readLine().trim());
            sorted[i] = height[i];
        }

        Arrays.sort(sorted);

        // count how many positions differ between original and sorted
        int diff = 0;
        for (int i = 0; i < n; i++) {
            if (height[i] != sorted[i]) diff++;
        }

        // number of swaps is diff - 1, but at least 0
        int swaps = Math.max(0, diff - 1);

        pw.println(swaps);
        pw.close();
        br.close();
    }
}