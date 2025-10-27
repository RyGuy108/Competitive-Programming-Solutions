import java.io.*;
import java.util.*;

public class cardgame {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("cardgame.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cardgame.out")));

        int N = Integer.parseInt(br.readLine().trim()); // N is even
        int[] elsie = new int[N];
        boolean[] used = new boolean[2 * N + 1]; // 1..2N

        for (int i = 0; i < N; i++) {
            elsie[i] = Integer.parseInt(br.readLine().trim());
            used[elsie[i]] = true;
        }

        // Build Bessie's sorted cards (ascending)
        int[] bessie = new int[N];
        int idx = 0;
        for (int v = 1; v <= 2 * N; v++) {
            if (!used[v]) {
                bessie[idx++] = v;
            }
        }

        int half = N / 2;
        // low: bessie[0..half-1]  (ascending)
        // high: bessie[half..N-1] (ascending)

        int score = 0;

        // First half: Elsie's first half rounds: indices 0..half-1
        // Use Bessie's high cards (bessie[half..N-1]) greedily (smallest > elsie)
        int j = half; // pointer into high (ascending)
        for (int i = 0; i < half; i++) {
            int e = elsie[i];
            while (j < N && bessie[j] <= e) j++;
            if (j == N) break; // no more high cards that can beat this or any later Elsie card
            score++;
            j++; // use this high card
        }

        // Second half: Elsie's second half rounds: indices half..N-1
        // Use Bessie's low cards (bessie[0..half-1]) greedily in reverse:
        // iterate Elsie's second half from the end to start and match with largest low card available
        j = half - 1; // pointer into low (largest in low)
        for (int i = N - 1; i >= half; i--) {
            int e = elsie[i];
            while (j >= 0 && bessie[j] <= e) j--;
            if (j < 0) break; // no low card can beat this or any earlier Elsie card (in reverse)
            score++;
            j--; // use that low card
        }

        pw.println(score);
        pw.close();
        br.close();
    }
}