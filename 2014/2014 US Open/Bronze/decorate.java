import java.io.*;
import java.util.*;

public class decorate {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("decorate.in"));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        // adjacency list
        ArrayList<Integer>[] adj = new ArrayList[N];
        for (int i = 0; i < N; i++) adj[i] = new ArrayList<>();

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()) - 1;
            int v = Integer.parseInt(st.nextToken()) - 1;
            // Add undirected edge
            adj[u].add(v);
            adj[v].add(u);
        }
        br.close();

        int[] color = new int[N];           // -1 = unvisited, 0/1 = two colors
        Arrays.fill(color, -1);

        long result = 0;
        boolean impossible = false;

        // For each component, BFS from an uncolored node
        ArrayDeque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < N && !impossible; i++) {
            if (color[i] != -1) continue;

            // start new component
            int[] cnt = new int[2];
            color[i] = 0;
            cnt[0] = 1;
            cnt[1] = 0;
            q.clear();
            q.add(i);

            while (!q.isEmpty() && !impossible) {
                int u = q.removeFirst();
                int cu = color[u];
                for (int v : adj[u]) {
                    if (color[v] == -1) {
                        color[v] = 1 - cu;
                        cnt[color[v]]++;
                        q.add(v);
                    } else if (color[v] == cu) {
                        // Not bipartite
                        impossible = true;
                        break;
                    }
                }
            }

            if (!impossible) {
                result += Math.max(cnt[0], cnt[1]);
            }
        }

        PrintWriter pw = new PrintWriter(new FileWriter("decorate.out"));
        if (impossible) pw.println(-1);
        else pw.println(result);
        pw.close();
    }
}