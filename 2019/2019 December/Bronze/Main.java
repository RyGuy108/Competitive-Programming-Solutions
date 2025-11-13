import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int K = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());
        int[][] pos = new int[K][N+1];
        for (int k = 0; k < K; k++) {
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                int cow = Integer.parseInt(st.nextToken());
                pos[k][cow] = i;
            }
        }
        int ans = 0;
        for (int a = 1; a <= N; a++) {
            for (int b = 1; b <= N; b++) {
                boolean ok = true;
                for (int k = 0; k < K; k++) {
                    if (pos[k][a] >= pos[k][b]) { ok = false; break; }
                }
                if (ok) ans++;
            }
        }
        System.out.println(ans);
    }
}
