import java.io.*;
import java.util.*;

public class homework {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("homework.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("homework.out")));

        int n = Integer.parseInt(br.readLine().trim());
        int[] scores = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            if (!st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
            scores[i] = Integer.parseInt(st.nextToken());
        }

        // We'll iterate suffixes starting at index i (i from n-1 down to 1).
        // If Bessie eats the first K problems, then i == K (first remaining index).
        // For a suffix starting at i, remaining count = n - i, after removing smallest we average over (n-i-1).
        long bestNumer = 0;     // numerator = sum_of_suffix - min_of_suffix
        long bestDenom = 1;     // denominator = n - i - 1
        LinkedList<Integer> answers = new LinkedList<>();

        long suffixSum = 0;
        long suffixMin = Long.MAX_VALUE;

        for (int i = n - 1; i > 0; i--) {
            // extend suffix to include scores[i]
            suffixSum += scores[i];
            suffixMin = Math.min(suffixMin, scores[i]);

            // we need at least two remaining problems (n-i >= 2) so denom >= 1
            if (i <= n - 2) {
                long numer = suffixSum - suffixMin;        // sum after removing minimum
                long denom = (n - i - 1);                  // number of terms averaged
                // Compare numer/denom to bestNumer/bestDenom without division:
                long lhs = numer * bestDenom;
                long rhs = bestNumer * denom;
                if (lhs > rhs) {
                    // strictly better average -> reset answers
                    answers.clear();
                    bestNumer = numer;
                    bestDenom = denom;
                    answers.addFirst(i); // add current K = i
                } else if (lhs == rhs) {
                    // equal average -> add this K as well
                    answers.addFirst(i);
                }
            }
        }

        // print answers (one per line) in increasing order (they were added using addFirst during descending scan)
        for (int k : answers) pw.println(k);
        pw.close();
        br.close();
    }
}