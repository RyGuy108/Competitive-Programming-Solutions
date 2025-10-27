import java.io.*;
import java.util.*;

public class cowTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("cow.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cow.out")));

        int N = Integer.parseInt(br.readLine().trim());
        String s = br.readLine().trim();
        // safety: if input may have spaces or extra lines, ensure we have the string of length N
        while (s.length() < N) {
            String more = br.readLine();
            if (more == null) break;
            s += more.trim();
        }

        long countC = 0L;
        long countCO = 0L;
        long countCOW = 0L;

        for (int i = 0; i < N; i++) {
            char ch = s.charAt(i);
            if (ch == 'C') {
                countC++;
            } else if (ch == 'O') {
                countCO += countC;
            } else if (ch == 'W') {
                countCOW += countCO;
            }
        }

        pw.println(countCOW);
        pw.close();
        br.close();
    }
}