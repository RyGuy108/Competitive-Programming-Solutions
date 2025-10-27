import java.io.*;
import java.util.*;

public class highcard {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("highcard.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("highcard.out")));
        int n = Integer.parseInt(br.readLine().trim());

        boolean[] elsieHas = new boolean[2 * n + 1];
        for (int i = 0; i < n; i++) {
            int v = Integer.parseInt(br.readLine().trim());
            elsieHas[v] = true;
        }

        ArrayList<Integer> bessie = new ArrayList<>(n);
        ArrayList<Integer> elsie = new ArrayList<>(n);
        for (int i = 1; i <= 2 * n; i++) {
            if (elsieHas[i]) elsie.add(i);
            else bessie.add(i);
        }

        int bi = 0, ei = 0, points = 0;
        while (bi < n && ei < n) {
            if (bessie.get(bi) > elsie.get(ei)) {
                points++;
                bi++; ei++;
            } else {
                bi++;
            }
        }

        pw.println(points);
        pw.close();
        br.close();
    }
}