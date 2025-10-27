import java.io.*;
import java.util.*;

public class shuffle {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("shuffle.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("shuffle.out")));

        int n = Integer.parseInt(br.readLine().trim());
        int[] to = new int[n];
        int[] indeg = new int[n];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            to[i] = Integer.parseInt(st.nextToken()) - 1; // convert to 0-based
            indeg[to[i]]++;
        }

        int remaining = n;
        ArrayDeque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (indeg[i] == 0) {
                q.add(i);
                remaining--;
            }
        }

        while (!q.isEmpty()) {
            int curr = q.removeFirst();
            int nxt = to[curr];
            indeg[nxt]--;
            if (indeg[nxt] == 0) {
                q.add(nxt);
                remaining--;
            }
        }

        pw.println(remaining);
        pw.close();
        br.close();
    }
}