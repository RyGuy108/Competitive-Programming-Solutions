import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine().trim());
        int[] deg = new int[N];
        for (int i = 1; i < N; i++) {
            String line = br.readLine();
            while (line != null && line.trim().isEmpty()) line = br.readLine();
            StringTokenizer st = new StringTokenizer(line);
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            deg[a]++; deg[b]++;
        }
        int D = 0;
        for (int x : deg) if (x > D) D = x;
        System.out.println(D + 1);
    }
}