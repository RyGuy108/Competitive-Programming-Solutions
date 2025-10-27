import java.io.*;
import java.util.*;

public class censorTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("censor.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("censor.out")));

        String S = br.readLine();
        String T = br.readLine();
        br.close();

        int n = S.length();
        int m = T.length();

        // compute prefix (pi) for pattern T
        int[] pi = new int[m];
        for (int i = 1; i < m; i++) {
            int k = pi[i - 1];
            while (k > 0 && T.charAt(k) != T.charAt(i)) k = pi[k - 1];
            if (T.charAt(k) == T.charAt(i)) k++;
            pi[i] = k;
        }

        StringBuilder out = new StringBuilder();
        // lens[i] = length of matched prefix of T after writing out.charAt(i)
        int[] lens = new int[n];
        int sz = 0; // current length of out (and next write index into lens)

        char[] pat = T.toCharArray();
        for (int i = 0; i < n; i++) {
            char c = S.charAt(i);

            // previous matched length
            int k = (sz == 0) ? 0 : lens[sz - 1];
            while (k > 0 && pat[k] != c) k = pi[k - 1];
            if (pat[k] == c) k++;

            // append character and record new matched length
            out.append(c);
            lens[sz] = k;
            sz++;

            // if we matched full pattern, remove it
            if (k == m) {
                // drop last m chars
                sz -= m;
                out.setLength(sz);
                // nothing to change in lens (we will overwrite as we append more chars)
            }
        }

        pw.println(out.toString());
        pw.close();
    }
}