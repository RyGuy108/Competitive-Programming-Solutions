import java.io.*;
import java.util.*;

public class hopscotchTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("hopscotch.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("hopscotch.out")));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int R = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());

        char[][] grid = new char[R][C];
        for (int i = 0; i < R; i++) {
            String line = br.readLine().trim();
            // handle possible spaces or continuous characters
            if (line.length() != C) {
                // if characters separated by spaces
                StringTokenizer rowTok = new StringTokenizer(line);
                for (int j = 0; j < C; j++) grid[i][j] = rowTok.nextToken().charAt(0);
            } else {
                for (int j = 0; j < C; j++) grid[i][j] = line.charAt(j);
            }
        }

        long[][] dp = new long[R][C];
        dp[0][0] = 1L;

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (i == 0 && j == 0) continue;
                long ways = 0L;
                for (int r = 0; r < i; r++) {
                    for (int c = 0; c < j; c++) {
                        if (grid[r][c] != grid[i][j]) ways += dp[r][c];
                    }
                }
                dp[i][j] = ways;
            }
        }

        pw.println(dp[R-1][C-1]);
        pw.close();
        br.close();
    }
}