import java.io.*;
import java.util.*;

public class measurement {
    static class Event {
        int day;
        String cow;
        int delta;
        Event(int d, String c, int dv) { day = d; cow = c; delta = dv; }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("measurement.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("measurement.out")));

        int N = Integer.parseInt(br.readLine().trim());
        ArrayList<Event> ev = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            String line = br.readLine().trim();
            StringTokenizer st = new StringTokenizer(line);
            int day = Integer.parseInt(st.nextToken());
            String cow = st.nextToken();
            String sdelta = st.nextToken();
            int delta = Integer.parseInt(sdelta); // handles +3 or -1
            ev.add(new Event(day, cow, delta));
        }
        br.close();

        Collections.sort(ev, Comparator.comparingInt(e -> e.day));

        // initial production
        Map<String, Integer> prod = new HashMap<>();
        prod.put("Bessie", 7);
        prod.put("Elsie", 7);
        prod.put("Mildred", 7);

        // initial leaders: all three (tie)
        Set<String> leaders = new HashSet<>(Arrays.asList("Bessie","Elsie","Mildred"));

        int changes = 0;
        int idx = 0;
        while (idx < ev.size()) {
            int day = ev.get(idx).day;
            // apply all events on this day
            while (idx < ev.size() && ev.get(idx).day == day) {
                Event e = ev.get(idx);
                prod.put(e.cow, prod.get(e.cow) + e.delta);
                idx++;
            }
            // compute current max and set of leaders
            int maxVal = Collections.max(prod.values());
            Set<String> cur = new HashSet<>();
            for (Map.Entry<String,Integer> en : prod.entrySet()) {
                if (en.getValue() == maxVal) cur.add(en.getKey());
            }
            // if leaders changed, increment
            if (!cur.equals(leaders)) {
                changes++;
                leaders = cur;
            }
        }

        pw.println(changes);
        pw.close();
    }
}