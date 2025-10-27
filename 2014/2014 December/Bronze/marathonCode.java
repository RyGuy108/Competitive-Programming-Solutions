import java.io.*;
import java.util.*;

public class marathonCode {
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("marathon.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("marathon.out")));
        int N = Integer.parseInt(br.readLine().trim());
        int[] x = new int[N];
        int[] y = new int[N];
        
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            x[i] = Integer.parseInt(st.nextToken());
            y[i] = Integer.parseInt(st.nextToken());
        }

        long total = 0;
        for (int i = 0; i < N - 1; i++) {
            total += manhattan(x[i], y[i], x[i+1], y[i+1]);
        }

        long bestSave = 0;
        for (int i = 1; i <= N - 2; i++) {
            long save = manhattan(x[i-1], y[i-1], x[i], y[i]) + manhattan(x[i], y[i], x[i+1], y[i+1]) - manhattan(x[i-1], y[i-1], x[i+1], y[i+1]);
            if (save > bestSave) {
                bestSave = save;
            }
        }

        pw.println(total - bestSave);
        pw.close();
        br.close();

    }

    private static long manhattan(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
}
