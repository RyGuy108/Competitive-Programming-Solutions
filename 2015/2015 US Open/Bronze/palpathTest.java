import java.io.*;
import java.util.*;

public class palpathTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("palpath.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("palpath.out")));

        int N = Integer.parseInt(br.readLine().trim());
        char[][] g = new char[N][N];
        for (int i = 0; i < N; i++) {
            String line = br.readLine().trim();
            for (int j = 0; j < N; j++) g[i][j] = line.charAt(j);
        }

        // forward[r][c] = set of distinct strings from (0,0) to (r,c) (in order start -> ... -> (r,c))
        @SuppressWarnings("unchecked")
        HashSet<String>[][] forward = new HashSet[N][N];
        for (int i = 0; i < N; i++) for (int j = 0; j < N; j++) forward[i][j] = new HashSet<>();

        forward[0][0].add(String.valueOf(g[0][0]));
        // propagate along diagonals 0 .. N-2 so that we fill diagonal N-1
        for (int s = 0; s <= N - 2; s++) {
            for (int r = 0; r < N; r++) {
                int c = s - r;
                if (c < 0 || c >= N) continue;
                if (forward[r][c].isEmpty()) continue;
                // right
                if (c + 1 < N) {
                    char ch = g[r][c + 1];
                    HashSet<String> dest = forward[r][c + 1];
                    for (String str : forward[r][c]) dest.add(str + ch);
                }
                // down
                if (r + 1 < N) {
                    char ch = g[r + 1][c];
                    HashSet<String> dest = forward[r + 1][c];
                    for (String str : forward[r][c]) dest.add(str + ch);
                }
            }
        }

        // backward[r][c] = set of distinct strings from (N-1,N-1) to (r,c) **in order end -> ... -> (r,c)**
        @SuppressWarnings("unchecked")
        HashSet<String>[][] backward = new HashSet[N][N];
        for (int i = 0; i < N; i++) for (int j = 0; j < N; j++) backward[i][j] = new HashSet<>();

        backward[N - 1][N - 1].add(String.valueOf(g[N - 1][N - 1]));
        // propagate backwards along diagonals from 2N-2 down to N (so we fill diagonal N-1)
        for (int s = 2 * N - 2; s >= N; s--) {
            for (int r = 0; r < N; r++) {
                int c = s - r;
                if (c < 0 || c >= N) continue;
                if (backward[r][c].isEmpty()) continue;
                // left -> move to (r, c-1)
                if (c - 1 >= 0) {
                    char ch = g[r][c - 1];
                    HashSet<String> dest = backward[r][c - 1];
                    for (String str : backward[r][c]) dest.add(str + ch);
                }
                // up -> move to (r-1, c)
                if (r - 1 >= 0) {
                    char ch = g[r - 1][c];
                    HashSet<String> dest = backward[r - 1][c];
                    for (String str : backward[r][c]) dest.add(str + ch);
                }
            }
        }

        // For each middle cell r+c == N-1, intersect forward[r][c] and backward[r][c].
        // Use a global set to avoid counting the same palindrome twice.
        HashSet<String> palindromes = new HashSet<>();
        for (int r = 0; r < N; r++) {
            int c = (N - 1) - r;
            if (c < 0 || c >= N) continue;
            if (forward[r][c].isEmpty() || backward[r][c].isEmpty()) continue;
            // iterate smaller set
            HashSet<String> small = forward[r][c].size() <= backward[r][c].size() ? forward[r][c] : backward[r][c];
            HashSet<String> large = small == forward[r][c] ? backward[r][c] : forward[r][c];
            for (String sStr : small) {
                if (large.contains(sStr)) palindromes.add(sStr);
            }
        }

        pw.println(palindromes.size());
        pw.close();
        br.close();
    }
}