import java.io.*;
import java.util.*;

/**
 * Mooyo Mooyo solution in Java.
 *
 * Reads N, K and the N rows of the 10-wide board from "mooyomooyo.in",
 * simulates repeated removal of connected regions of size >= K and gravity,
 * and writes the final board to "mooyomooyo.out".
 */
public class MooyoMooyo {
    static int N, K;
    static int[][] board;       // board[r][c], 0..N-1 x 0..9
    static int[][] region;      // region id per cell during one labeling pass
    static int[] regsizes;      // size per region id (1-based)
    static final int W = 10;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("mooyomooyo.in"));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        board = new int[N][W];
        region = new int[N][W];
        // max possible distinct regions in one pass <= N*W, allocate a bit more
        regsizes = new int[N * W + 5];

        for (int i = 0; i < N; i++) {
            String s = br.readLine().trim();
            for (int j = 0; j < W; j++) {
                board[i][j] = s.charAt(j) - '0';
            }
        }
        br.close();

        // Repeat until no more regions removed
        while (iterateOnce()) {
            // loop until iterateOnce returns false
        }

        // Output final board
        PrintWriter out = new PrintWriter(new FileWriter("mooyomooyo.out"));
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < W; j++) {
                out.print(board[i][j]);
            }
            out.println();
        }
        out.close();
    }

    /**
     * Perform one full iteration:
     *  - label all connected nonzero regions and store their sizes in regsizes
     *  - any region with size >= K gets cleared (set cells to 0)
     *  - apply gravity to make values fall down in each column
     *
     * @return true if we removed at least one region this iteration (i.e. progress), else false.
     */
    static boolean iterateOnce() {
        // clear region labels
        for (int i = 0; i < N; i++) {
            Arrays.fill(region[i], 0);
        }
        Arrays.fill(regsizes, 0);

        int rId = 1; // region id starts at 1

        // Label every connected region (4-directionally) with DFS
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < W; j++) {
                if (board[i][j] != 0 && region[i][j] == 0) {
                    // explore this region, increment regsizes[rId]
                    dfsLabel(i, j, rId, board[i][j]);
                    rId++;
                }
            }
        }

        // If any region has size >= K, clear all its cells
        boolean removed = false;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < W; j++) {
                int id = region[i][j];
                if (id > 0 && regsizes[id] >= K) {
                    board[i][j] = 0;
                    removed = true;
                }
            }
        }

        if (removed) {
            applyGravity();
        }

        // zero out regsizes entries we used (not strictly necessary here
        // because we re-fill regsizes each iterate once, but keeps it clean)
        // (rId-1 is max region index used)
        // Arrays.fill(regsizes, 0); // not necessary because next iterateOnce fills regsizes

        return removed;
    }

    /**
     * Depth-first label for a connected region (recursive).
     * Marks region[][] = rId for all connected cells of color 'color' starting at (i,j),
     * and updates regsizes[rId].
     *
     * Note: recursion depth <= region size <= 1000 (safe for Java recursion).
     */
    static void dfsLabel(int i, int j, int rId, int color) {
        // bounds & checks
        if (i < 0 || i >= N || j < 0 || j >= W) return;
        if (board[i][j] != color) return;
        if (region[i][j] != 0) return;

        region[i][j] = rId;
        regsizes[rId]++;

        // 4-directional neighbors
        dfsLabel(i - 1, j, rId, color);
        dfsLabel(i + 1, j, rId, color);
        dfsLabel(i, j - 1, rId, color);
        dfsLabel(i, j + 1, rId, color);
    }

    /**
     * Apply gravity to every column: non-zero cells fall down to the bottom
     * preserving their order within the column.
     */
    static void applyGravity() {
        for (int col = 0; col < W; col++) {
            int write = N - 1; // position where we next write a non-zero cell
            // scan from bottom to top, copy non-zero values downwards
            for (int row = N - 1; row >= 0; row--) {
                if (board[row][col] != 0) {
                    board[write][col] = board[row][col];
                    write--;
                }
            }
            // fill remaining cells above with zero
            for (int row = write; row >= 0; row--) {
                board[row][col] = 0;
            }
        }
    }
}