import java.io.*;
import java.util.*;

public class cowroute {
    static final int MAXV = 1010; // supports city ids up to 1000
    static final long INF_COST = Long.MAX_VALUE / 4;
    static final int INF_FLIGHTS = Integer.MAX_VALUE / 4;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("cowroute.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cowroute.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int A = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());

        // costFirst[u][v] = minimal route-cost to go from u to v using a single route.
        // costSecond[u][v] = corresponding number of flights (edges) for that single-route travel (j-k).
        long[][] costFirst = new long[MAXV][MAXV];
        int[][] costSecond = new int[MAXV][MAXV];

        // initialize to INF
        for (int i = 0; i < MAXV; i++) {
            Arrays.fill(costFirst[i], INF_COST);
            Arrays.fill(costSecond[i], INF_FLIGHTS);
            costFirst[i][i] = 0;
            costSecond[i][i] = 0;
        }

        // Read routes and update direct cost entries
        for (int routeIdx = 0; routeIdx < N; routeIdx++) {
            // read line containing route_cost and route_len
            st = new StringTokenizer(br.readLine());
            long routeCost = Long.parseLong(st.nextToken());
            int routeLen = Integer.parseInt(st.nextToken());

            // read routeLen city ids (they are given on the next line(s))
            int[] route = new int[routeLen];
            int filled = 0;
            while (filled < routeLen) {
                st = new StringTokenizer(br.readLine());
                while (st.hasMoreTokens() && filled < routeLen) {
                    route[filled++] = Integer.parseInt(st.nextToken());
                }
            }

            // For each pair k < j in this route, we can board at route[k] and leave at route[j]
            for (int j = 0; j < routeLen; j++) {
                for (int k = 0; k < j; k++) {
                    int u = route[k];
                    int v = route[j];
                    int flights = j - k; // number of individual flights (edges) when traveling in that route segment
                    // If routeCost is strictly better, or same cost but fewer flights, update
                    if (routeCost < costFirst[u][v] ||
                        (routeCost == costFirst[u][v] && flights < costSecond[u][v])) {
                        costFirst[u][v] = routeCost;
                        costSecond[u][v] = flights;
                    }
                }
            }
        }

        // Dijkstra-like relaxation without heap: compute min (cost, flights) from A to every city
        boolean[] vis = new boolean[MAXV];
        long[] distCost = new long[MAXV];
        int[] distFlights = new int[MAXV];
        Arrays.fill(distCost, INF_COST);
        Arrays.fill(distFlights, INF_FLIGHTS);
        distCost[A] = 0;
        distFlights[A] = 0;

        for (int iter = 0; iter < MAXV; iter++) {
            // pick the unvisited vertex with smallest pair (distCost, distFlights)
            int u = -1;
            for (int v = 0; v < MAXV; v++) {
                if (vis[v]) continue;
                if (u == -1) {
                    if (distCost[v] < INF_COST) u = v;
                    else if (distCost[v] == INF_COST && distFlights[v] < INF_FLIGHTS) u = v;
                } else {
                    if (distCost[v] < distCost[u] ||
                       (distCost[v] == distCost[u] && distFlights[v] < distFlights[u])) {
                        u = v;
                    }
                }
            }
            if (u == -1) break; // no reachable unvisited vertex remaining
            vis[u] = true;

            // relax edges from u
            for (int v = 0; v < MAXV; v++) {
                if (costFirst[u][v] == INF_COST) continue; // no single-route connection u->v
                long newCost = distCost[u];
                int newFlights = distFlights[u];
                if (newCost == INF_COST) continue; // unreachable u
                newCost += costFirst[u][v];
                newFlights += costSecond[u][v];
                // compare pairs
                if (newCost < distCost[v] || (newCost == distCost[v] && newFlights < distFlights[v])) {
                    distCost[v] = newCost;
                    distFlights[v] = newFlights;
                }
            }
        }

        if (distCost[B] == INF_COST) {
            pw.println("-1 -1");
        } else {
            pw.println(distCost[B] + " " + distFlights[B]);
        }

        pw.close();
        br.close();
    }
}