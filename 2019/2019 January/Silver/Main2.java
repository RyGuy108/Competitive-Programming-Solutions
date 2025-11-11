import java.io.*;
import java.util.*;

public class Main2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        while (line != null && line.trim().isEmpty()) line = br.readLine();
        int N = Integer.parseInt(line.trim());
        int M = N + 2;
        char[][] grid = new char[M][M];
        for (int i = 0; i < M; i++) Arrays.fill(grid[i], '.');
        for (int i = 1; i <= N; i++) {
            String s = br.readLine();
            while (s != null && s.length() < N) s = br.readLine();
            for (int j = 1; j <= N; j++) grid[i][j] = s.charAt(j - 1);
        }
        int[][] region = new int[M][M];
        int maxCells = N * N + 5;
        int[] area = new int[maxCells];
        int[] perimeter = new int[maxCells];
        int R = 0;
        int size = M * M;
        int[] stack = new int[size];
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (grid[i][j] == '#' && region[i][j] == 0) {
                    R++;
                    int top = 0;
                    stack[top++] = i * M + j;
                    while (top > 0) {
                        int v = stack[--top];
                        int r = v / M, c = v % M;
                        if (region[r][c] != 0 || grid[r][c] == '.') continue;
                        region[r][c] = R;
                        area[R]++;
                        int v1 = (r-1)*M + c;
                        int v2 = (r+1)*M + c;
                        int v3 = r*M + (c-1);
                        int v4 = r*M + (c+1);
                        stack[top++] = v1;
                        stack[top++] = v2;
                        stack[top++] = v3;
                        stack[top++] = v4;
                    }
                }
            }
        }
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                int id = region[i][j];
                if (id == 0) continue;
                if (region[i-1][j] == 0) perimeter[id]++;
                if (region[i+1][j] == 0) perimeter[id]++;
                if (region[i][j-1] == 0) perimeter[id]++;
                if (region[i][j+1] == 0) perimeter[id]++;
            }
        }
        int bestA = 0, bestP = 0;
        for (int r = 1; r <= R; r++) {
            if (area[r] > bestA || (area[r] == bestA && perimeter[r] < bestP)) {
                bestA = area[r];
                bestP = perimeter[r];
            }
        }
        System.out.println(bestA + " " + bestP);
    }
}