import java.io.*;
import java.util.*;

/*
 * Translated from the provided C++ solution to Java.
 * Computes shortest hop distances from nodes 0, 1, and N-1 using BFS,
 * then minimizes D0[meet]*B + D1[meet]*E + Dn[meet]*P over all meet nodes.
 *
 * Input: B E P N M
 * then M undirected edges (1-based indices)
 *
 * Output: minimal total energy (long)
 */
public class piggyback {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("piggyback.in"));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long B = Long.parseLong(st.nextToken());
        long E = Long.parseLong(st.nextToken());
        long P = Long.parseLong(st.nextToken());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        ArrayList<Integer>[] adj = new ArrayList[N];
        for (int i = 0; i < N; i++) adj[i] = new ArrayList<>();

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            adj[x].add(y);
            adj[y].add(x);
        }
        br.close();

        long[] D0 = bfs(0, adj);
        long[] D1 = bfs(1, adj);
        long[] Dn = bfs(N - 1, adj);

        long ans = Long.MAX_VALUE;
        for (int meet = 0; meet < N; meet++) {
            if (D0[meet] == -1 || D1[meet] == -1 || Dn[meet] == -1) continue;
            long cost = D0[meet] * B + D1[meet] * E + Dn[meet] * P;
            if (cost < ans) ans = cost;
        }

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("piggyback.out")));
        pw.println(ans);
        pw.close();
    }

    // BFS returning distances (number of edges) as long[]; -1 if unreachable
    static long[] bfs(int start, ArrayList<Integer>[] adj) {
        int n = adj.length;
        long[] dist = new long[n];
        Arrays.fill(dist, -1L);
        ArrayDeque<Integer> q = new ArrayDeque<>();
        dist[start] = 0;
        q.addLast(start);
        while (!q.isEmpty()) {
            int u = q.removeFirst();
            for (int v : adj[u]) {
                if (dist[v] == -1L) {
                    dist[v] = dist[u] + 1;
                    q.addLast(v);
                }
            }
        }
        return dist;
    }
}