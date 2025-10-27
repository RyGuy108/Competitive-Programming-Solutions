import java.io.*;
import java.util.*;

public class CowSignal {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("cowsignal.in"));
        PrintWriter pw = new PrintWriter(new FileWriter("cowsignal.out"));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int M = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken()); // took all values

        for (int i = 0; i < M; i++) { // get in all lines in a for loop
            String row = br.readLine(); // get in a line

            StringBuilder stretched = new StringBuilder(N * K);
            for (int j = 0; j < N; j++) { // go through each of the characters
                char ch = row.charAt(j);
                for (int x = 0; x < K; x++) {  // repetion by K
                    stretched.append(ch);
                }
            }

            for (int y = 0; y < K; y++) {
                pw.println(stretched);
            }
        }

        

        pw.close();
    }
}