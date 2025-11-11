import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int[] A = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            if (!st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
            A[i] = Integer.parseInt(st.nextToken());
        }
        int ans = N - 1;
        for (int i = N - 2; i >= 0; i--) {
            if (A[i] < A[i + 1]) ans = i;
            else break;
        }
        System.out.println(ans);
    }
}