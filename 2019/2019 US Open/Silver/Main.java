package Silver;

import java.io.*;
import java.util.*;

public class Main {
    static int N;
    static int[][] g;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine().trim());
        g = new int[N][N];
        for (int i = 0; i < N; i++) {
            String s = br.readLine().trim();
            for (int j = 0; j < N; j++) {
                g[i][j] = (s.charAt(j) == 'L') ? 1 : 0;
            }
        }

        for (int i = 1; i < N; i++) {
            g[i][0] = g[i][0] ^ g[0][0];
            for (int j = 1; j < N; j++) {
                g[i][j] = g[i][j] ^ g[0][j] ^ g[i][0];
            }
        }

        if (count(1,1,N-1,N-1,0) == 0) {
            System.out.println("1 1");
            return;
        }

        if (count(1,1,N-1,N-1,1) == N-1) {
            for (int j = 1; j < N; j++) {
                if (count(1,j,N-1,j,1) == N-1) {
                    System.out.println("1 " + (j+1));
                    return;
                }
            }
            for (int i = 1; i < N; i++) {
                if (count(i,1,i,N-1,1) == N-1) {
                    System.out.println((i+1) + " 1");
                    return;
                }
            }
            System.out.println("-1");
            return;
        }

        if (count(1,1,N-1,N-1,1) != 1) {
            System.out.println("-1");
            return;
        }

        for (int i = 1; i < N; i++) {
            for (int j = 1; j < N; j++) {
                if (g[i][j] == 1) {
                    System.out.println((i+1) + " " + (j+1));
                    return;
                }
            }
        }

        System.out.println("-1");
    }

    static int count(int r1, int c1, int r2, int c2, int val) {
        int tot = 0;
        for (int i = r1; i <= r2; i++) {
            for (int j = c1; j <= c2; j++) {
                if (g[i][j] == val) tot++;
            }
        }
        return tot;
    }
}