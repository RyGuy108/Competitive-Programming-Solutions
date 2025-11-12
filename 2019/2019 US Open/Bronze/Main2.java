import java.io.*;
import java.util.*;

public class Main2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int[] out = new int[N+1];
        for (int i = 0; i < N-1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            out[a]++;
        }
        int answer = -1;
        for (int i = 1; i <= N; i++) {
            if (out[i] == 0) {
                if (answer != -1) { answer = -1; break; }
                answer = i;
            }
        }
        System.out.println(answer);
    }
}