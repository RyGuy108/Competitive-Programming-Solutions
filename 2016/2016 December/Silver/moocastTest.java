import java.io.*;
import java.util.*;

public class moocastTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("moocast.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("moocast.out")));
        int N = Integer.parseInt(br.readLine().trim());
        int[] x = new int[N], y = new int[N], p = new int[N];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            x[i] = Integer.parseInt(st.nextToken());
            y[i] = Integer.parseInt(st.nextToken());
            p[i] = Integer.parseInt(st.nextToken());
        }

        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] adj = new ArrayList[N];
        for (int i = 0; i < N; i++) adj[i] = new ArrayList<>();

        // build directed edges i -> j if i can reach j
        for (int i = 0; i < N; i++) {
            long pr = (long) p[i] * p[i];
            for (int j = 0; j < N; j++) {
                if (i == j) continue;
                long dx = x[i] - x[j];
                long dy = y[i] - y[j];
                if (dx*dx + dy*dy <= pr) adj[i].add(j);
            }
        }

        int best = 0;
        // for each cow do BFS to count reachable nodes
        for (int i = 0; i < N; i++) {
            boolean[] seen = new boolean[N];
            Deque<Integer> q = new ArrayDeque<>();
            q.add(i);
            seen[i] = true;
            int cnt = 0;
            while (!q.isEmpty()) {
                int u = q.poll();
                cnt++;
                for (int v : adj[u]) {
                    if (!seen[v]) {
                        seen[v] = true;
                        q.add(v);
                    }
                }
            }
            best = Math.max(best, cnt);
        }

        pw.println(best);
        pw.close();
        br.close();
    }
}