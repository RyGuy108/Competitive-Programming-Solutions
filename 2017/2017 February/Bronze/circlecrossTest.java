import java.io.*;
import java.util.*;

public class circlecrossTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("circlecross.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("circlecross.out")));

        String s = br.readLine().trim(); // 52 chars, each letter A..Z appears exactly twice
        int[] first = new int[26];
        int[] second = new int[26];
        Arrays.fill(first, -1);
        Arrays.fill(second, -1);

        for (int i = 0; i < s.length(); i++) {
            int idx = s.charAt(i) - 'A';
            if (first[idx] == -1) first[idx] = i;
            else second[idx] = i;
        }

        int crossings = 0;
        for (int a = 0; a < 26; a++) {
            for (int b = a + 1; b < 26; b++) {
                // check if intervals [first[a], second[a]] and [first[b], second[b]] interleave
                if (first[a] < first[b] && first[b] < second[a] && second[a] < second[b]) crossings++;
                else if (first[b] < first[a] && first[a] < second[b] && second[b] < second[a]) crossings++;
            }
        }

        pw.println(crossings);
        pw.close();
        br.close();
    }
}