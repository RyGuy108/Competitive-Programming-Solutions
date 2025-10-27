import java.io.*;
import java.util.*;

/**
 * Java solution for "slowdown" following the priority-queue approach.
 * Reads N and N events (each "T x" or "D x") from stdin and prints
 * the total time (rounded to nearest integer, 0.5 rounds up).
 */
public class slowdown {
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int N = fs.nextInt();

        // Min-heaps for events
        PriorityQueue<Integer> timeEvents = new PriorityQueue<>();
        PriorityQueue<Integer> distEvents = new PriorityQueue<>();

        for (int i = 0; i < N; i++) {
            String typ = fs.next();   // "T" or "D"
            int x = fs.nextInt();
            if (typ.charAt(0) == 'T') timeEvents.add(x);
            else distEvents.add(x);
        }

        // Always push the final D=1000 landmark (finish at 1000 meters)
        distEvents.add(1000);

        double currentT = 0.0;   // current time in seconds
        double currentD = 0.0;   // current distance in meters
        int slowCount = 1;       // current slowdown factor: speed = 1 / slowCount (initially 1 m/s)

        // Process until all events consumed (timeEvents may become empty; distEvents still has sentinel)
        while (!timeEvents.isEmpty() || !distEvents.isEmpty()) {
            boolean chooseTimeEvent = false;

            if (distEvents.isEmpty()) {
                // no distance events remaining -> must use time event
                chooseTimeEvent = true;
            } else if (timeEvents.isEmpty()) {
                // no time events remaining -> must use distance event
                chooseTimeEvent = false;
            } else {
                // Compare actual next time of the earliest distance event (if we keep current speed)
                // with the earliest time event.
                int nextT = timeEvents.peek();
                int nextD = distEvents.peek();

                // time when the distance event happens if we keep current slowCount (speed = 1/slowCount):
                // timeToCover = (nextD - currentD) / speed = (nextD - currentD) * slowCount
                double timeIfDoDistance = currentT + (nextD - currentD) * slowCount;

                // if nextT occurs before that, next event is the time-event
                if (nextT < timeIfDoDistance) chooseTimeEvent = true;
                else chooseTimeEvent = false;
            }

            if (chooseTimeEvent) {
                // Advance to the given time event
                int eventT = timeEvents.poll();
                // distance progressed during (eventT - currentT) seconds at current speed (1/slowCount)
                double deltaT = eventT - currentT;                 // seconds
                double deltaD = deltaT / (slowCount + 0.0);        // meters
                currentD += deltaD;
                currentT = eventT;
                slowCount++;
            } else {
                // Advance to the given distance event
                int eventD = distEvents.poll();
                // time needed to progress from currentD to eventD at speed 1/slowCount:
                double deltaD = eventD - currentD;
                double deltaT = deltaD * slowCount;
                currentT += deltaT;
                currentD = eventD;
                slowCount++;
            }
        }

        // Round currentT to nearest integer; for ties (.5) Math.round uses half-up for positive numbers
        long answer = Math.round(currentT);

        System.out.println(answer);
    }

    // Minimal fast scanner for ints and tokens
    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        FastScanner(InputStream is) { in = is; }
        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }
        public String next() throws IOException {
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = read()) <= ' ') if (c == -1) return null;
            do { sb.append((char)c); c = read(); } while (c > ' ');
            return sb.toString();
        }
        public int nextInt() throws IOException { return Integer.parseInt(next()); }
    }
}