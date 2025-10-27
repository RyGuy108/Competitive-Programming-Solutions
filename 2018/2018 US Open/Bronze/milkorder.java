import java.io.*;
import java.util.*;

public class milkorder {
    static int nCows, M, nFixed;
    static int[] ord;         // sequence of M cows (0-indexed)
    static int[] cFixed;      // fixed cows indices (0-indexed)
    static int[] pFixed;      // their fixed positions (0-indexed)

    static boolean[] usedCow;
    static boolean[] usedPos;
    static int[] pos;         // assigned position for cow i (if assigned)

    // check whether current set of fixed assignments (including cow 1 placed)
    // can produce a valid ordering consistent with the M-ordered cows
    static boolean works() {
        Arrays.fill(usedCow, false);
        Arrays.fill(usedPos, false);

        // apply fixed assignments
        for (int i = 0; i < nFixed; i++) {
            int cow = cFixed[i];
            int p = pFixed[i];

            if (usedCow[cow]) {
                // if already assigned, it must match the same position
                if (pos[cow] == p) continue;
                return false;
            }
            if (usedPos[p]) return false;

            usedCow[cow] = true;
            usedPos[p] = true;
            pos[cow] = p;
        }

        int j = 0; // next free position we can place a cow not in fixed list
        for (int i = 0; i < M; i++) {
            int cow = ord[i];
            if (usedCow[cow]) {
                // cow is already assigned; we must not have passed its position
                if (j > pos[cow]) return false;
                // set j to that position (keep j there so next placement may use same slot
                // only if it's already used; the next while loop will skip used slots)
                j = pos[cow];
                continue;
            }
            // find the next unoccupied position >= j
            while (j < nCows && usedPos[j]) j++;
            if (j == nCows) return false; // no position left
            usedPos[j] = true;
            pos[cow] = j;
            // leave j as-is; next iteration will increment/skips if needed
        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("milkorder.in"));
        StringTokenizer st = new StringTokenizer(br.readLine());
        nCows = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        nFixed = Integer.parseInt(st.nextToken());

        ord = new int[M];
        if (M > 0) {
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < M; i++) {
                ord[i] = Integer.parseInt(st.nextToken()) - 1; // convert to 0-index
            }
        } else {
            // read the empty line if present (some inputs omit)
            // nothing to do
        }

        cFixed = new int[nCows + 1]; // allow space for one extra fixed (cow 1)
        pFixed = new int[nCows + 1];
        for (int i = 0; i < nFixed; i++) {
            st = new StringTokenizer(br.readLine());
            cFixed[i] = Integer.parseInt(st.nextToken()) - 1;
            pFixed[i] = Integer.parseInt(st.nextToken()) - 1;
        }
        br.close();

        // We'll append cow 1 (index 0) as the last fixed constraint in the arrays.
        // So increase nFixed by 1 to reserve that slot.
        int origFixed = nFixed;
        nFixed = origFixed + 1;

        usedCow = new boolean[nCows];
        usedPos = new boolean[nCows];
        pos = new int[nCows];

        int answer = -1;
        // try each position p for cow 1 from earliest (0) to latest (nCows-1)
        for (int p = 0; p < nCows; p++) {
            cFixed[nFixed - 1] = 0; // cow 1 has index 0
            pFixed[nFixed - 1] = p;
            if (works()) {
                answer = p + 1; // convert back to 1-indexed position for output
                break;
            }
        }

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("milkorder.out")));
        pw.println(answer);
        pw.close();
    }
}