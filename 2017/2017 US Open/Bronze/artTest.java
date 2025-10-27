import java.io.*;
import java.util.*;

public class artTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("art.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("art.out")));

        int N = Integer.parseInt(br.readLine().trim());
        int[][] grid = new int[N][N];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) grid[i][j] = Integer.parseInt(st.nextToken());
        }

        final int MAXC = 10; // colors 0..9, 0 = blank (ignore)
        boolean[] present = new boolean[MAXC];
        int[] minR = new int[MAXC], minC = new int[MAXC], maxR = new int[MAXC], maxC = new int[MAXC];
        Arrays.fill(minR, Integer.MAX_VALUE);
        Arrays.fill(minC, Integer.MAX_VALUE);
        Arrays.fill(maxR, Integer.MIN_VALUE);
        Arrays.fill(maxC, Integer.MIN_VALUE);

        // collect bounding boxes and presence
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                int col = grid[r][c];
                if (col == 0) continue; // ignore blank
                present[col] = true;
                minR[col] = Math.min(minR[col], r);
                minC[col] = Math.min(minC[col], c);
                maxR[col] = Math.max(maxR[col], r);
                maxC[col] = Math.max(maxC[col], c);
            }
        }

        // colors that cannot be first because they appear inside some other color's bounding box
        boolean[] cannotBeFirst = new boolean[MAXC];

        for (int color = 1; color < MAXC; color++) {
            if (!present[color]) continue;
            for (int r = minR[color]; r <= maxR[color]; r++) {
                for (int c = minC[color]; c <= maxC[color]; c++) {
                    int inside = grid[r][c];
                    if (inside != color && inside != 0) {
                        // 'inside' must have been painted after 'color'
                        cannotBeFirst[inside] = true;
                    }
                }
            }
        }

        int answer = 0;
        for (int color = 1; color < MAXC; color++) {
            if (present[color] && !cannotBeFirst[color]) answer++;
        }

        pw.println(answer);
        pw.close();
        br.close();
    }
}