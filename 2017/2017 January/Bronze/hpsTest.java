import java.io.*;
import java.util.*;

public class hpsTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("hps.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("hps.out")));

        int N = Integer.parseInt(br.readLine().trim());
        int[] a = new int[N];
        int[] b = new int[N];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            a[i] = Integer.parseInt(st.nextToken());
            b[i] = Integer.parseInt(st.nextToken());
        }

        // We represent gestures by integers:
        // 0 = hoof, 1 = paper, 2 = scissors
        // The rule: g1 beats g2 iff (g1 - g2 + 3) % 3 == 1
        int[] perm = new int[]{0,1,2}; // will permute these values assigned to labels 1,2,3
        int best = 0;

        // Try all 6 permutations for mapping {label 1,2,3} -> {hoof,paper,scissors}
        List<int[]> permutations = generatePermutations(perm);
        for (int[] p : permutations) {
            // p[0] is gesture assigned to label 1, p[1] to label 2, p[2] to label 3
            int wins = 0;
            for (int i = 0; i < N; i++) {
                int g1 = p[a[i]-1];
                int g2 = p[b[i]-1];
                if ((g1 - g2 + 3) % 3 == 1) wins++;
            }
            best = Math.max(best, wins);
        }

        pw.println(best);
        pw.close();
        br.close();
    }

    // Generate all permutations of an array of length 3
    private static List<int[]> generatePermutations(int[] base) {
        List<int[]> res = new ArrayList<>();
        permute(base, 0, res);
        return res;
    }

    private static void permute(int[] arr, int idx, List<int[]> out) {
        if (idx == arr.length) {
            out.add(Arrays.copyOf(arr, arr.length));
            return;
        }
        for (int i = idx; i < arr.length; i++) {
            swap(arr, idx, i);
            permute(arr, idx + 1, out);
            swap(arr, idx, i);
        }
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}