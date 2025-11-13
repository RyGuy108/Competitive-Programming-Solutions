import java.io.*;
import java.util.*;

public class Main3 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        String s = br.readLine().trim();
        char[] col = new char[N+1];
        for (int i = 1; i <= N; i++) col[i] = s.charAt(i-1);
        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] adj = new ArrayList[N+1];
        for (int i = 1; i <= N; i++) adj[i] = new ArrayList<>();
        for (int i = 0; i < N-1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            adj[a].add(b);
            adj[b].add(a);
        }
        int[] comp = new int[N+1];
        int cid = 0;
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        for (int i = 1; i <= N; i++) {
            if (comp[i] != 0) continue;
            cid++;
            stack.clear();
            stack.push(i);
            while (!stack.isEmpty()) {
                int u = stack.pop();
                if (comp[u] != 0) continue;
                comp[u] = cid;
                for (int v : adj[u]) {
                    if (comp[v] == 0 && col[v] == col[u]) stack.push(v);
                }
            }
        }
        StringBuilder sb = new StringBuilder(M);
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            char want = st.nextToken().charAt(0);
            if (comp[a] != comp[b] || col[a] == want) sb.append('1');
            else sb.append('0');
        }
        System.out.println(sb.toString());
    }
}