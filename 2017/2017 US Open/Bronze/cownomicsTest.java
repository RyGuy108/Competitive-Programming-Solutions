import java.io.*;
import java.util.*;

public class cownomicsTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("cownomics.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cownomics.out")));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        String[] spotty = new String[N];
        String[] plain = new String[N];
        for (int i = 0; i < N; i++) spotty[i] = br.readLine().trim();
        for (int i = 0; i < N; i++) plain[i] = br.readLine().trim();

        int count = 0;
        for (int pos = 0; pos < M; pos++) {
            boolean[] seen = new boolean[26]; // which letters appear in spotty at this position
            for (int i = 0; i < N; i++) {
                char ch = spotty[i].charAt(pos);
                seen[ch - 'A'] = true;
            }
            boolean good = true;
            for (int i = 0; i < N && good; i++) {
                char ch = plain[i].charAt(pos);
                if (seen[ch - 'A']) good = false; // plain cow has a letter that spotty cows have here
            }
            if (good) count++;
        }

        pw.println(count);
        pw.close();
        br.close();
    }
}