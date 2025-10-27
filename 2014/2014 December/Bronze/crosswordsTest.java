import java.io.*;
import java.util.*;

public class crosswordsTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("crosswords.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("crosswords.out")));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        char[][] g = new char[N][M];
        for (int i = 0; i < N; i++) {
            g[i] = br.readLine().toCharArray();
        }

        List<int[]> starts = new ArrayList<>();
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                if (g[r][c] == '#') continue;
                boolean startsH = false, startsV = false;

                // horizontal: left blocked/outside AND two cells to right are clear
                boolean leftBlocked = (c - 1 < 0) || (g[r][c - 1] == '#');
                if (leftBlocked && c + 2 < M && g[r][c + 1] == '.' && g[r][c + 2] == '.') {
                    startsH = true;
                }

                // vertical: above blocked/outside AND two cells below are clear
                boolean aboveBlocked = (r - 1 < 0) || (g[r - 1][c] == '#');
                if (aboveBlocked && r + 2 < N && g[r + 1][c] == '.' && g[r + 2][c] == '.') {
                    startsV = true;
                }

                if (startsH || startsV) {
                    starts.add(new int[]{r + 1, c + 1}); // store 1-based coordinates
                }
            }
        }

        pw.println(starts.size());
        for (int[] p : starts) pw.println(p[0] + " " + p[1]);

        pw.close();
        br.close();
    }
}