import java.io.*;
import java.util.*;

public class bcount {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("bcount.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("bcount.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        // prefix[b][i] = number of breed b among first i cows (1-based indexing)
        int[][] prefix = new int[4][n + 1];

        for (int i = 1; i <= n; i++) {
            int curr = Integer.parseInt(br.readLine().trim());
            // copy previous counts
            for (int b = 1; b <= 3; b++) prefix[b][i] = prefix[b][i - 1];
            // increment this breed
            prefix[curr][i]++;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c1 = prefix[1][b] - prefix[1][a - 1];
            int c2 = prefix[2][b] - prefix[2][a - 1];
            int c3 = prefix[3][b] - prefix[3][a - 1];
            sb.append(c1).append(' ').append(c2).append(' ').append(c3).append('\n');
        }

        pw.print(sb.toString());
        pw.close();
        br.close();
    }
}