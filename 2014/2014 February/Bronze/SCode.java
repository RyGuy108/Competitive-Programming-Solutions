import java.io.*;
import java.util.*;

/**
 * SCode solution (Java).
 *
 * This follows the recursive approach from the reference:
 *  - For a string s of odd length L = 2*h + 1:
 *      check up to 4 patterns that show s could have been formed from
 *      a shorter string by one operation; recurse on the shorter string.
 *  - Base case: if L is even or L == 1, return 1.
 *  - Final answer = numways(s) - 1 (we require >= 1 operations).
 *
 * We memoize results for strings we've already computed.
 */
public class SCode {
    static Map<String, Long> memo = new HashMap<>();

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("scode.in"));
        String s = br.readLine().trim();
        br.close();

        long ways = numways(s) - 1; // subtract the "no-op" base case
        if (ways < 0) ways = 0;
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("scode.out")));
        pw.println(ways);
        pw.close();
    }

    // count number of ways to obtain string s (including the trivial base case)
    static long numways(String s) {
        if (memo.containsKey(s)) return memo.get(s);

        int L = s.length();
        // Base case: if length is even or length is 1, there's exactly 1 way
        // (we treat this as a base configuration)
        if (L % 2 == 0 || L == 1) {
            memo.put(s, 1L);
            return 1L;
        }

        long ans = 1L; // count the base case
        int h = L / 2; // L = 2*h + 1

        // Helper for substrings that mimic C++ substr(pos, len)
        // s.substring(a, b) in Java is [a, b)
        // We'll translate C++ s.substr(pos,len) -> s.substring(pos, pos+len)

        // Case 1:
        // if s.substr(0,h) == s.substr(h,h)
        //    ans += numways(s.substr(h, h+1))
        if (equalSub(s, 0, h, h, h)) {
            String next = s.substring(h, h + h + 1); // length h+1 starting at h
            ans += numways(next);
        }

        // Case 2:
        // if s.substr(0,h) == s.substr(h+1,h)
        //    ans += numways(s.substr(0,h+1))
        if (h + 1 + h <= L && equalSub(s, 0, h, h + 1, h)) {
            String next = s.substring(0, h + 1);
            ans += numways(next);
        }

        // Case 3:
        // if s.substr(0,h) == s.substr(h+1,h)
        //    ans += numways(s.substr(h,h+1))
        if (h + 1 + h <= L && equalSub(s, 0, h, h + 1, h)) {
            String next = s.substring(h, h + h + 1);
            ans += numways(next);
        }

        // Case 4:
        // if s.substr(1,h) == s.substr(h+1,h)
        //    ans += numways(s.substr(0,h+1))
        if (1 + h <= L && h + 1 + h <= L && equalSub(s, 1, h, h + 1, h)) {
            String next = s.substring(0, h + 1);
            ans += numways(next);
        }

        memo.put(s, ans);
        return ans;
    }

    // compare substrings s[pos1 .. pos1+len1-1] and s[pos2 .. pos2+len2-1]
    // in all uses here len1 == len2, but function supports lengths explicitly
    static boolean equalSub(String s, int pos1, int len1, int pos2, int len2) {
        if (pos1 < 0 || pos2 < 0) return false;
        if (pos1 + len1 > s.length() || pos2 + len2 > s.length()) return false;
        if (len1 != len2) return false;
        return s.regionMatches(pos1, s, pos2, len1);
    }
}