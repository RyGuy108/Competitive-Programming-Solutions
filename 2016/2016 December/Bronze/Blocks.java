import java.io.*;
import java.util.*;

public class Blocks {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("blocks.in"));
        PrintWriter pw = new PrintWriter(new FileWriter("blocks.out"));

        int N = Integer.parseInt(br.readLine().trim());
        // blocksNeeded[i] = total number of i+'a' blocks we must have
        int[] blocksNeeded = new int[26];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String w1 = st.nextToken();
            String w2 = st.nextToken();

            // count freq of each letter in w1 and w2
            int[] freq1 = freq(w1);
            int[] freq2 = freq(w2);

            // for each letter a..z, we need the max count from this pair
            for (int c = 0; c < 26; c++) {
                blocksNeeded[c] += Math.max(freq1[c], freq2[c]);
            }
        }

        // output one line per letter
        for (int c = 0; c < 26; c++) {
            pw.println(blocksNeeded[c]);
        }
        pw.close();
    }

    /** Returns a length-26 array of letter counts for s ('a'→0, …, 'z'→25). */
    private static int[] freq(String s) {
        int[] f = new int[26];
        for (int i = 0; i < s.length(); i++) {
            f[s.charAt(i) - 'a']++;
        }
        return f;
    }
}