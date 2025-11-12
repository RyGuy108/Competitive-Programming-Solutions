import java.io.*;
import java.util.*;

public class Main2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[] A = new int[M];
        int[] B = new int[M];
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            if (a > b) { int t = a; a = b; b = t; }
            A[i] = a;
            B[i] = b;
        }
        int[] G = new int[N + 1];
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= N; i++) {
            int g;
            for (g = 1; g <= 4; g++) {
                boolean ok = true;
                for (int j = 0; j < M; j++)
                    if (B[j] == i && G[A[j]] == g) { ok = false; break; }
                if (ok) break;
            }
            G[i] = g;
            sb.append(g);
        }
        System.out.println(sb.toString());
    }
}