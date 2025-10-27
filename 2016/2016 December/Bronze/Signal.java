import java.io.*;
import java.util.*;

public class Signal {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("cowsignal.in"));
        PrintWriter  pw = new PrintWriter(new FileWriter("cowsignal.out"));

        // Read M, N, K
        StringTokenizer st = new StringTokenizer(br.readLine());
        int M = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken()); // M x N is the total paper
        int K = Integer.parseInt(st.nextToken()); // is the transformation amount

        // Process each of the M input rows
        for (int i = 0; i < M; i++) {
            String row = br.readLine(); // get in a row like XXX.

            // Build the horizontally–stretched version once
            StringBuilder stretched = new StringBuilder(N * K); // the amount of characters in a line
            for (int j = 0; j < N; j++) { // go through each of the characters in a line
                char ch = row.charAt(j); // gets the character
                // repeat this character K times
                for (int x = 0; x < K; x++) {
                    stretched.append(ch);
                }
            }

            // Now print that stretched row K times (vertical repetition)
            for (int y = 0; y < K; y++) {
                pw.println(stretched);
            }
        }

        pw.close();
    }
}