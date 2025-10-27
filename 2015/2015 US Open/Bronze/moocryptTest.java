import java.io.*;
import java.util.*;

public class moocryptTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("moocrypt.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("moocrypt.out")));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int R = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());
        char[][] g = new char[R][C];
        for (int i = 0; i < R; i++) {
            String line = br.readLine().trim();
            // line contains exactly C uppercase letters
            if (line.length() != C) {
                // handle spaced input if any
                StringTokenizer rowTok = new StringTokenizer(line);
                for (int j = 0; j < C; j++) g[i][j] = rowTok.nextToken().charAt(0);
            } else {
                for (int j = 0; j < C; j++) g[i][j] = line.charAt(j);
            }
        }

        // counts[a][b] = number of length-3 straight triples with letters (a,b,b)
        long[][] counts = new long[26][26];

        // 8 directions
        int[] dr = {-1,-1,-1,0,0,1,1,1};
        int[] dc = {-1,0,1,-1,1,-1,0,1};

        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                for (int d = 0; d < 8; d++) {
                    int r1 = r + dr[d], c1 = c + dc[d];
                    int r2 = r + 2*dr[d], c2 = c + 2*dc[d];
                    if (r1 < 0 || r1 >= R || c1 < 0 || c1 >= C) continue;
                    if (r2 < 0 || r2 >= R || c2 < 0 || c2 >= C) continue;
                    int a = g[r][c] - 'A';
                    int b = g[r1][c1] - 'A';
                    int b2 = g[r2][c2] - 'A';
                    if (b != b2) continue;
                    counts[a][b]++;
                }
            }
        }

        // choose best pair (x,y) where x != y, x != 'M', y != 'O'
        int Midx = 'M' - 'A';
        int Oidx = 'O' - 'A';
        long best = 0;
        for (int x = 0; x < 26; x++) {
            if (x == Midx) continue;           // cannot map cipher x to 'M' if x == 'M'
            for (int y = 0; y < 26; y++) {
                if (y == Oidx) continue;       // cannot map cipher y to 'O' if y == 'O'
                if (x == y) continue;          // must be distinct cipher letters
                if (counts[x][y] > best) best = counts[x][y];
            }
        }

        pw.println(best);
        pw.close();
        br.close();
    }
}