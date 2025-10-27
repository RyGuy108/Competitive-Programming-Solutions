import java.io.*;
import java.util.*;

public class Cowart {
    static int N;
    static char[][] grid;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("cowart.in"));
        N = Integer.parseInt(br.readLine().trim());
        grid = new char[N][N];
        for (int i = 0; i < N; i++) {
            String s = br.readLine().trim();
            grid[i] = s.toCharArray();
        }
        br.close();

        // Count regions for human (R,G,B all distinct)
        int human = countRegions(false);

        // Count regions for cow (R and G indistinguishable)
        int cow = countRegions(true);

        PrintWriter pw = new PrintWriter(new FileWriter("cowart.out"));
        pw.println(human + " " + cow);
        pw.close();
    }

    /**
     * Count connected regions using 4-neighbor connectivity.
     * If mergeRG is true, treat 'R' and 'G' as the same color.
     */
    static int countRegions(boolean mergeRG) {
        boolean[][] visited = new boolean[N][N];
        int regions = 0;
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (!visited[r][c]) {
                    regions++;
                    floodFill(r, c, visited, mergeRG);
                }
            }
        }
        return regions;
    }

    // 4-direction vectors
    static final int[] dr = {-1, 1, 0, 0};
    static final int[] dc = {0, 0, -1, 1};

    static void floodFill(int sr, int sc, boolean[][] visited, boolean mergeRG) {
        char base = grid[sr][sc];
        // iterative stack (avoid recursion depth issues though N<=100 recursion would be fine)
        ArrayDeque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[]{sr, sc});
        visited[sr][sc] = true;

        while (!stack.isEmpty()) {
            int[] p = stack.pop();
            int r = p[0], c = p[1];
            for (int k = 0; k < 4; k++) {
                int nr = r + dr[k], nc = c + dc[k];
                if (nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
                if (visited[nr][nc]) continue;
                if (equivalent(base, grid[nr][nc], mergeRG)) {
                    visited[nr][nc] = true;
                    stack.push(new int[]{nr, nc});
                }
            }
        }
    }

    // Return true if color a and b are considered the same under the given vision model
    static boolean equivalent(char a, char b, boolean mergeRG) {
        if (a == b) return true;
        if (mergeRG) {
            // treat R and G as identical for cow vision
            return (a == 'R' || a == 'G') && (b == 'R' || b == 'G');
        }
        return false;
    }
}