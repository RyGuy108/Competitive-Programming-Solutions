import java.io.*;
import java.util.*;

public class BlockGame {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("blocks.in"));
        PrintWriter pw = new PrintWriter(new FileWriter("blocks.out"));

        int N = Integer.parseInt(br.readLine().trim()); // the amount of blocks
        int[] blocksNeeded = new int[26];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String w1 = st.nextToken();
            String w2 = st.nextToken();

            int[] freq1 = freq(w1); // give array of every letter in the word
            int[] freq2 = freq(w2); // give array of every letter in the word

            for (int c = 0; c < 26; c++) { // goes through alphabet
                blocksNeeded[c] += Math.max(freq1[c], freq2[c]);
            }
        }

        for (int c = 0; c < 26; c++) {
            pw.println(blocksNeeded[c]);
        }

        pw.close();
    }

    private static int[] freq(String s) {
        int[] f = new int[26];
        for (int i = 0; i < s.length(); i++) {// goes through length of word
            f[s.charAt(i) - 'a']++;
        }
        return f;
    }
}