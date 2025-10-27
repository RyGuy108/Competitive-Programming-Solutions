import java.io.*;
import java.util.*;

public class cowroute2Test {
    static final int MAXCITY = 10000;
    static final int INF = Integer.MAX_VALUE / 4;

    // store up to two best (cost, routeId) pairs per city on each side
    static class Best {
        int cost1 = INF, id1 = -1;
        int cost2 = INF, id2 = -1;

        void consider(int cost, int id) {
            if (id == id1) {
                if (cost < cost1) cost1 = cost;
            } else if (id == id2) {
                if (cost < cost2) cost2 = cost;
            } else {
                if (cost < cost1) {
                    // demote best1 to best2
                    cost2 = cost1; id2 = id1;
                    cost1 = cost; id1 = id;
                } else if (cost < cost2) {
                    cost2 = cost; id2 = id;
                }
            }
        }

        // return up to two pairs as list (cost,id), ignoring INF entries
        List<int[]> pairs() {
            List<int[]> res = new ArrayList<>(2);
            if (cost1 < INF) res.add(new int[]{cost1, id1});
            if (cost2 < INF) res.add(new int[]{cost2, id2});
            return res;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("cowroute.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cowroute.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int A = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());

        Best[] fromA = new Best[MAXCITY + 1];
        Best[] toB   = new Best[MAXCITY + 1];
        for (int i = 0; i <= MAXCITY; i++) {
            fromA[i] = new Best();
            toB[i]   = new Best();
        }

        int bestSingle = INF;

        for (int routeId = 0; routeId < N; routeId++) {
            st = new StringTokenizer(br.readLine());
            int cost = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());
            int[] route = new int[m];
            int posA = -1, posB = -1;
            for (int i = 0; i < m; i++) {
                route[i] = Integer.parseInt(st.nextToken());
                if (route[i] == A && posA == -1) posA = i;
                if (route[i] == B && posB == -1) posB = i;
            }

            // if route contains both A then B in order -> single-route candidate
            if (posA != -1 && posB != -1 && posA < posB) {
                bestSingle = Math.min(bestSingle, cost);
            }

            // if route contains A, any city to the right of A can be reached on this route
            if (posA != -1) {
                for (int k = posA + 1; k < m; k++) {
                    int city = route[k];
                    fromA[city].consider(cost, routeId);
                }
            }

            // if route contains B, any city to the left of B can be used as transfer point
            if (posB != -1) {
                for (int k = 0; k < posB; k++) {
                    int city = route[k];
                    toB[city].consider(cost, routeId);
                }
            }
        }

        long best = bestSingle; // may be INF

        // try combining two routes via each possible transfer city
        for (int city = 1; city <= MAXCITY; city++) {
            List<int[]> left = fromA[city].pairs(); // (cost, id) up to 2
            List<int[]> right = toB[city].pairs(); // (cost, id) up to 2
            if (left.isEmpty() || right.isEmpty()) continue;

            // try all combinations of up to two candidates on each side,
            // ensuring we don't pick the same route id for both legs
            for (int[] L : left) {
                for (int[] R : right) {
                    if (L[1] != R[1]) {
                        long sum = (long)L[0] + (long)R[0];
                        if (sum < best) best = sum;
                    }
                }
            }
        }

        if (best >= INF) pw.println(-1);
        else pw.println(best);

        pw.close();
        br.close();
    }
}