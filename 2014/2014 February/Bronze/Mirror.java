import java.io.*;
import java.util.*;

/**
 * Mirror / beam tracing solution (Java translation of the provided C++ code).
 *
 * Reads N, M and a grid of '/' and '\' characters, then simulates a beam
 * starting from every edge cell (entering the board) and tracks the count
 * of bounces until the beam leaves the grid. Prints the maximum bounce count.
 *
 * Input:  mirror.in
 * Output: mirror.out
 */
public class Mirror {
    // direction: 0 (down), 1 (right), 2 (up), 3 (left)
    static final int[] dr = {1, 0, -1, 0};
    static final int[] dc = {0, 1, 0, -1};

    // new direction after hitting '/' mirror
    static final int[] bounceSlash = {3, 2, 1, 0};

    // new direction after hitting '\' mirror
    static final int[] bounceBackslash = {1, 0, 3, 2};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("mirror.in"));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        String[] grid = new String[N];
        for (int i = 0; i < N; i++) {
            grid[i] = br.readLine();
        }
        br.close();

        int best = 0;

        // Start from left and right edges
        for (int r = 0; r < N; r++) {
            best = Math.max(best, trace(grid, N, M, r, 0, 1));       // entering at (r,0) heading right
            best = Math.max(best, trace(grid, N, M, r, M - 1, 3));   // entering at (r,M-1) heading left
        }

        // Start from top and bottom edges
        for (int c = 0; c < M; c++) {
            best = Math.max(best, trace(grid, N, M, 0, c, 0));       // entering at (0,c) heading down
            best = Math.max(best, trace(grid, N, M, N - 1, c, 2));   // entering at (N-1,c) heading up
        }

        PrintWriter pw = new PrintWriter(new FileWriter("mirror.out"));
        pw.println(best);
        pw.close();
    }

    /**
     * Simulate the beam starting at (r, c) with initial direction dir.
     * Returns number of mirror bounces (steps) until it leaves the grid.
     */
    static int trace(String[] grid, int N, int M, int r0, int c0, int dir0) {
        int r = r0;
        int c = c0;
        int dir = dir0;
        int result = 0;

        while (r >= 0 && r < N && c >= 0 && c < M) {
            char ch = grid[r].charAt(c);
            if (ch == '/') {
                dir = bounceSlash[dir];
            } else { // assume the other possibility is backslash '\'
                // In the input it's guaranteed each cell is '/' or '\'
                // in Java we represent backslash char as '\\'
                dir = bounceBackslash[dir];
            }
            // move one step in the current direction
            r += dr[dir];
            c += dc[dir];
            result++;
        }
        return result;
    }
}