import java.io.*;
import java.util.*;

public class bphoto {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("bphoto.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("bphoto.out")));
        int n = Integer.parseInt(br.readLine().trim());
        State[] arr = new State[n];
        for (int i = 0; i < n; i++) {
            int h = Integer.parseInt(br.readLine().trim());
            arr[i] = new State(h, i);
        }
        // sort descending by height
        Arrays.sort(arr);
        int unbalanced = 0;
        int seen = 0;
        BIT bit = new BIT(n);
        for (State curr : arr) {
            int lhs = bit.query(curr.index);      // number of taller cows to the left
            int rhs = seen - lhs;                // number of taller cows to the right
            int big = Math.max(lhs, rhs);
            int small = Math.min(lhs, rhs);
            if (small > 0 && big > 2 * small) {
                unbalanced++;
            } else if (small == 0 && big > 0 && big > 0 * 2) {
                // small==0 -> condition reduces to big > 0 (2*0 = 0) 
                // but problem definition: if min=0, then check if max > 2*min => max>0 -> any nonzero would mark unbalanced.
                // This matches the logic: if all taller cows are on one side and there are any, it's unbalanced.
                unbalanced++;
            }
            bit.update(curr.index, 1);
            seen++;
        }
        pw.println(unbalanced);
        pw.close();
        br.close();
    }

    static class State implements Comparable<State> {
        int height;
        int index;
        State(int h, int idx) { height = h; index = idx; }
        // sort descending by height
        public int compareTo(State o) {
            return o.height - this.height;
        }
    }

    // 0-indexed BIT, internal uses 1..n
    static class BIT {
        int[] tree;
        BIT(int n) { tree = new int[n + 5]; }
        void update(int idx, int val) {
            idx++; // shift to 1-based
            while (idx < tree.length) {
                tree[idx] += val;
                idx += idx & -idx;
            }
        }
        int query(int idx) {
            if (idx < 0) return 0;
            int res = 0;
            idx++; // shift to 1-based
            while (idx > 0) {
                res += tree[idx];
                idx -= idx & -idx;
            }
            return res;
        }
    }
}