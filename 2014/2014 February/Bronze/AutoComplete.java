import java.io.*;
import java.util.*;

/**
 * Auto-complete solution (Java translation of the provided C++ approach).
 *
 * Reads W (number of dictionary words) and N (number of queries).
 * Then W words, then N queries: each query is (K, prefix).
 *
 * For each query we:
 *  - binary-search the sorted dictionary to find the first word >= prefix,
 *  - check the word at position (first + K - 1) to see if it startsWith(prefix).
 *  - if yes, print the original 1-based index of that dictionary word; otherwise -1.
 */
public class AutoComplete {
    static class Entry {
        String word;
        int origIndex;
        Entry(String w, int idx) { word = w; origIndex = idx; }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("auto.in"));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int W = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());

        Entry[] dict = new Entry[W];
        for (int i = 0; i < W; i++) {
            String s = br.readLine();
            dict[i] = new Entry(s, i);
        }
        // sort lexicographically by word
        Arrays.sort(dict, new Comparator<Entry>() {
            public int compare(Entry a, Entry b) {
                return a.word.compareTo(b.word);
            }
        });

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("auto.out")));

        for (int qi = 0; qi < N; qi++) {
            st = new StringTokenizer(br.readLine());
            int k = Integer.parseInt(st.nextToken());
            String pref = st.nextToken();

            // find first position with word >= pref (lower_bound)
            int pos = lowerBound(dict, pref);

            int candidate = pos + (k - 1); // k is 1-based in input
            if (candidate < W && dict[candidate].word.startsWith(pref)) {
                pw.println(dict[candidate].origIndex + 1); // 1-based original index
            } else {
                pw.println(-1);
            }
        }

        pw.close();
        br.close();
    }

    // Binary search to find first index i such that dict[i].word.compareTo(pref) >= 0
    static int lowerBound(Entry[] dict, String pref) {
        int lo = 0, hi = dict.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (dict[mid].word.compareTo(pref) < 0) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }
}