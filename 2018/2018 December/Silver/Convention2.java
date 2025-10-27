import java.io.*;
import java.util.*;

public class Convention2 {
    static class Cow {
        long arrival;
        int seniority;   // input order (0 = most senior)
        long duration;
        Cow(long a, int s, long d) { arrival = a; seniority = s; duration = d; }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("convention2.in"));
        int N = Integer.parseInt(br.readLine().trim());
        ArrayList<Cow> cows = new ArrayList<>(N);

        // input order = seniority; first read line has seniority 0 (most senior)
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            long a = Long.parseLong(st.nextToken());
            long t = Long.parseLong(st.nextToken());
            cows.add(new Cow(a, i, t));
        }
        br.close();

        // Sort by arrival time, then by seniority (since we stored seniority in the object,
        // Collections.sort will compare arrival then seniority if we ask it to).
        Collections.sort(cows, (c1, c2) -> {
            if (c1.arrival != c2.arrival) return Long.compare(c1.arrival, c2.arrival);
            return Integer.compare(c1.seniority, c2.seniority);
        });

        // PriorityQueue stores indices into 'cows' list. Comparator orders by seniority (smaller = higher).
        PriorityQueue<Integer> waiting = new PriorityQueue<>(Comparator.comparingInt(i -> cows.get(i).seniority));

        long answer = 0L;
        int next = 1; // index of next cow not yet processed into waiting/started (we take cows[0] as first scheduled)
        long currentFinished = cows.get(0).arrival + cows.get(0).duration;

        while (next < N || !waiting.isEmpty()) {
            // Add all cows who arrive while current cow is eating
            while (next < N && cows.get(next).arrival <= currentFinished) {
                waiting.add(next);
                next++;
            }

            if (waiting.isEmpty()) {
                // No one waiting: jump to next cow's arrival and start them immediately
                currentFinished = cows.get(next).arrival + cows.get(next).duration;
                next++;
            } else {
                // Serve the most senior waiting cow
                int idx = waiting.poll();
                long waitTime = currentFinished - cows.get(idx).arrival;
                if (waitTime > answer) answer = waitTime;
                currentFinished += cows.get(idx).duration;
            }
        }

        PrintWriter out = new PrintWriter(new FileWriter("convention2.out"));
        out.println(answer);
        out.close();
    }
}