import java.io.*;
import java.util.*;

public class hps {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("hps.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("hps.out")));

        int n = Integer.parseInt(br.readLine().trim());
        int[] seq = new int[n];
        int[] rev = new int[n];

        for (int i = 0; i < n; i++) {
            String s = br.readLine().trim();
            int v;
            if (s.equals("P")) v = 1;
            else if (s.equals("S")) v = 2;
            else v = 0; // H
            seq[i] = v;
            rev[n - 1 - i] = v;
        }
        br.close();

        // matchPrefix[g][i] = number of times gesture g appears in seq[0..i-1]
        int[][] matchPrefix = getMatch(seq);
        // matchSuffix is built as prefix counts of reversed array so
        // matchSuffix[g][k] = number of times gesture g appears in last k elements of original
        int[][] matchSuffix = getMatch(rev);

        int best = 0;
        // try split after a elements in prefix (prefix length = a, suffix length = n-a)
        for (int a = 0; a <= n; a++) {
            for (int g1 = 0; g1 < 3; g1++) {
                for (int g2 = 0; g2 < 3; g2++) {
                    int leftWins = matchPrefix[g1][a];         // wins if Bessie plays g1 for first a games
                    int rightWins = matchSuffix[g2][n - a];    // wins if Bessie plays g2 for last n-a games
                    best = Math.max(best, leftWins + rightWins);
                }
            }
        }

        pw.println(best);
        pw.close();
    }

    // build counts for gestures 0..2; matches[g][i] = count of g in prefix length i
    static int[][] getMatch(int[] arr) {
        int n = arr.length;
        int[][] matches = new int[3][n + 1];
        for (int i = 0; i < n; i++) {
            for (int g = 0; g < 3; g++) matches[g][i + 1] = matches[g][i];
            matches[arr[i]][i + 1]++;
        }
        return matches;
    }
}