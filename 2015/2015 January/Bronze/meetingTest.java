import java.io.*;
import java.util.*;

public class meetingTest {
    static class Edge { int to, bTime, eTime; Edge(int to, int b, int e){ this.to = to; bTime = b; eTime = e; } }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("meeting.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("meeting.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        // adjacency list; edges go from smaller index -> larger index (guaranteed A < B)
        ArrayList<Edge>[] adj = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) adj[i] = new ArrayList<>();

        int maxEdge = 0;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());
            int C = Integer.parseInt(st.nextToken()); // Bessie's time
            int D = Integer.parseInt(st.nextToken()); // Elsie's time
            adj[A].add(new Edge(B, C, D));
            if (C > maxEdge) maxEdge = C;
            if (D > maxEdge) maxEdge = D;
        }

        // Upper bound on times: (N-1) * 1000 (since at most N-1 edges, each ≤1000)
        int MAXT = (N - 1) * 1000;

        boolean[][] reachB = new boolean[N + 1][MAXT + 1];
        boolean[][] reachE = new boolean[N + 1][MAXT + 1];

        // start at node 1 at time 0
        reachB[1][0] = true;
        reachE[1][0] = true;

        // process nodes in increasing order (DAG topological order by node index)
        for (int u = 1; u <= N; u++) {
            // propagate Bessie's times from u to its neighbors
            for (int t = 0; t <= MAXT; t++) {
                if (!reachB[u][t]) continue;
                for (Edge e : adj[u]) {
                    int nt = t + e.bTime;
                    if (nt <= MAXT) reachB[e.to][nt] = true;
                }
            }
            // propagate Elsie's times from u to its neighbors
            for (int t = 0; t <= MAXT; t++) {
                if (!reachE[u][t]) continue;
                for (Edge e : adj[u]) {
                    int nt = t + e.eTime;
                    if (nt <= MAXT) reachE[e.to][nt] = true;
                }
            }
        }

        // find smallest common reachable time at node N
        int answer = -1;
        for (int t = 0; t <= MAXT; t++) {
            if (reachB[N][t] && reachE[N][t]) { answer = t; break; }
        }

        if (answer == -1) pw.println("IMPOSSIBLE");
        else pw.println(answer);

        pw.close();
        br.close();
    }
}