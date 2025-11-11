import java.io.*;
import java.util.*;

public class Irrigation {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("irrigation.in"));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());

        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            x[i] = Integer.parseInt(st.nextToken());
            y[i] = Integer.parseInt(st.nextToken());
        }
        br.close();

        if (n == 1) {
            try (PrintWriter pw = new PrintWriter(new FileWriter("irrigation.out"))) {
                pw.println(0);
            }
            return;
        }

        int[][] mat = new int[n][n];
        for (int i = 0; i < n; i++) {
            mat[i][i] = 0;
            for (int j = i + 1; j < n; j++) {
                int dx = x[i] - x[j];
                int dy = y[i] - y[j];
                int dist2 = dx * dx + dy * dy;
                if (dist2 >= C) {
                    mat[i][j] = dist2;
                    mat[j][i] = dist2;
                } else {
                    mat[i][j] = 0;
                    mat[j][i] = 0;
                }
            }
        }

        long result = prim(mat, n);
        PrintWriter pw = new PrintWriter(new FileWriter("irrigation.out"));
        pw.println(result);
        pw.close();
    }

    static long prim(int[][] mat, int n) {
        final int NIL = -1;
        boolean[] used = new boolean[n];
        int[] from = new int[n];    // from[j] = vertex that offers best edge to j
        Arrays.fill(from, NIL);
        Arrays.fill(used, false);

        int cur = 0;    // start from vertex 0
        long total = 0L;

        // We need to add n-1 edges
        for (int step = 0; step < n - 1; step++) {
            used[cur] = true;

            // Update best incoming edges for all unmarked vertices
            for (int j = 0; j < n; j++) {
                if (!used[j] && mat[cur][j] != 0) {
                    if (from[j] == NIL || mat[from[j]][j] > mat[cur][j]) {
                        from[j] = cur;
                    }
                }
            }

            // Choose next vertex x with minimal mat[from[x]][x]
            int next = -1;
            for (int j = 0; j < n; j++) {
                if (!used[j] && from[j] != NIL) {
                    if (next == -1 || mat[from[next]][next] > mat[from[j]][j]) {
                        next = j;
                    }
                }
            }

            if (next == -1) return -1L;

            total += mat[from[next]][next];
            cur = next;
        }

        return total;
    }
}