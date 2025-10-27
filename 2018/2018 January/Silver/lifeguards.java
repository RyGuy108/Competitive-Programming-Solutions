import java.io.*;
import java.util.*;

public class lifeguards {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("lifeguards.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("lifeguards.out")));

        int n = Integer.parseInt(br.readLine());
        State[] pts = new State[2 * n];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            pts[2 * i] = new State(s, i);     // start
            pts[2 * i + 1] = new State(e, i); // end
        }

        Arrays.sort(pts);

        TreeSet<Integer> active = new TreeSet<>();
        int last = pts[0].time;
        int totalCovered = 0;
        int[] alone = new int[n];

        for (State cur : pts) {
            // time since last event
            int dt = cur.time - last;

            // if exactly one lifeguard was active, that guard was "alone"
            if (active.size() == 1) {
                alone[active.first()] += dt;
            }
            // if at least one active, that span contributes to coverage
            if (!active.isEmpty()) {
                totalCovered += dt;
            }

            // toggle this lifeguard's active status
            if (active.contains(cur.idx)) active.remove(cur.idx);
            else active.add(cur.idx);

            last = cur.time;
        }

        int ans = 0; // if n==1, totalCovered - alone[0] = 0
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, totalCovered - alone[i]);
        }

        pw.println(ans);
        pw.close();
    }

    static class State implements Comparable<State> {
        int time, idx;
        State(int t, int i) { time = t; idx = i; }
        public int compareTo(State o) { return Integer.compare(this.time, o.time); }
    }
}