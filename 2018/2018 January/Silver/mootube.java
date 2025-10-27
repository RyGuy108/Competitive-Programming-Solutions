import java.io.*;
import java.util.*;

public class mootube {
    static class Edge {
        int to, w;
        Edge(int t, int w) { this.to = t; this.w = w; }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("mootube.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("mootube.out")));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        @SuppressWarnings("unchecked")
        List<Edge>[] g = new ArrayList[n];
        for (int i = 0; i < n; i++) g[i] = new ArrayList<>();

        // tree edges (n-1)
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int w = Integer.parseInt(st.nextToken());
            g[a].add(new Edge(b, w));
            g[b].add(new Edge(a, w));
        }

        // process each query with a BFS limited by threshold K
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int k = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken()) - 1;

            boolean[] seen = new boolean[n];
            Deque<Integer> dq = new ArrayDeque<>();
            dq.add(v);
            seen[v] = true;

            int count = 0;
            while (!dq.isEmpty()) {
                int u = dq.poll();
                for (Edge e : g[u]) {
                    if (!seen[e.to] && e.w >= k) {
                        seen[e.to] = true;
                        dq.add(e.to);
                        count++;
                    }
                }
            }
            pw.println(count);
        }

        pw.close();
    }
}