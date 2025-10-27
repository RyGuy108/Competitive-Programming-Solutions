import java.io.*;
import java.util.*;

public class cowtipTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("cowtip.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cowtip.out")));

        int N = Integer.parseInt(br.readLine().trim());
        boolean[][] grid = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            String line = br.readLine().trim();
            for (int j = 0; j < N; j++) {
                grid[i][j] = (line.charAt(j) == '1');
            }
        }

        int ops = 0;

        // Greedy: scan from bottom-right to top-left. If a cell is 1, flip the
        // rectangle with that cell as the bottom-right corner (i.e., upper-left
        // rectangle from (0,0) to (i,j)). This is optimal.
        for (int i = N - 1; i >= 0; i--) {
            for (int j = N - 1; j >= 0; j--) {
                if (grid[i][j]) {
                    ops++;
                    // toggle every cell in rectangle (0..i, 0..j)
                    for (int r = 0; r <= i; r++) {
                        for (int c = 0; c <= j; c++) {
                            grid[r][c] = !grid[r][c];
                        }
                    }
                }
            }
        }

        pw.println(ops);
        pw.close();
        br.close();
    }
}