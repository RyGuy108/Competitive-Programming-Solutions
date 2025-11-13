import java.io.*;
import java.util.*;

public class Main {
    static class Edge { int to; int cost; int flow; Edge(int t,int c,int f){to=t;cost=c;flow=f;} }
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        ArrayList<Edge>[] adj = new ArrayList[N+1];
        for (int i=1;i<=N;i++) adj[i]=new ArrayList<>();
        TreeSet<Integer> flowsSet = new TreeSet<>();
        for (int i=0;i<M;i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int f = Integer.parseInt(st.nextToken());
            adj[a].add(new Edge(b,c,f));
            adj[b].add(new Edge(a,c,f));
            flowsSet.add(f);
        }
        long bestNum = 0, bestDen = 1;
        int[] flows = new int[flowsSet.size()];
        int idx=0;
        for (int v: flowsSet) flows[idx++]=v;
        for (int f : flows) {
            long dist = dijkstra(N, adj, f);
            if (dist>=0) {
                long curNum = f;
                long curDen = dist;
                if (curNum * bestDen > bestNum * curDen) {
                    bestNum = curNum;
                    bestDen = curDen;
                }
            }
        }
        long result = bestNum * 1000000L / bestDen;
        System.out.println(result);
    }

    static long dijkstra(int N, ArrayList<Edge>[] adj, int minFlow) {
        long INF = Long.MAX_VALUE/4;
        long[] dist = new long[N+1];
        Arrays.fill(dist, INF);
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a->a[0]));
        dist[1]=0;
        pq.add(new long[]{0,1});
        while(!pq.isEmpty()){
            long[] cur = pq.poll();
            long d = cur[0];
            int u = (int)cur[1];
            if (d!=dist[u]) continue;
            if (u==N) return d;
            for (Edge e: adj[u]) {
                if (e.flow < minFlow) continue;
                int v = e.to;
                long nd = d + e.cost;
                if (nd < dist[v]) {
                    dist[v]=nd;
                    pq.add(new long[]{nd,v});
                }
            }
        }
        return -1;
    }
}