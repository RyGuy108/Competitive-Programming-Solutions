import java.io.*;
import java.util.*;

public class mooomoo {
    static final int INF = 1000000000;
    static ArrayList<Integer> breeds = new ArrayList<>();
    static ArrayList<Integer> volumes = new ArrayList<>();
    static ArrayList<Integer> knapsack = new ArrayList<>();

    static void readInput() throws Exception {
        FastScanner fs = new FastScanner(new File("mooomoo.in"));
        int n = fs.nextInt();
        int b = fs.nextInt();
        for (int i = 0; i < b; i++) breeds.add(fs.nextInt());
        for (int i = 0; i < n; i++) volumes.add(fs.nextInt());
    }

    static void extendKnapsack() {
        int t = knapsack.size();
        int v = INF;
        for (int coin : breeds) {
            int t2 = t - coin;
            if (t2 >= 0) {
                int prev = knapsack.get(t2);
                if (prev + 1 < v) v = prev + 1;
            }
        }
        knapsack.add(v);
    }

    static int getKnapsack(int total) throws Impossible {
        if (total < 0) throw new Impossible();
        while (total >= knapsack.size()) extendKnapsack();
        int val = knapsack.get(total);
        if (val >= INF) throw new Impossible();
        return val;
    }

    static int solve() throws Impossible {
        knapsack.clear();
        knapsack.add(0);
        int carry = 0;
        int res = 0;
        for (int i = 0; i < volumes.size(); i++) {
            carry = Math.max(carry - 1, 0);
            int need = volumes.get(i) - carry;
            res += getKnapsack(need);
            carry = volumes.get(i);
        }
        return res;
    }

    public static void main(String[] args) throws Exception {
        readInput();
        PrintWriter out = new PrintWriter(new FileWriter("mooomoo.out"));
        try {
            out.println(solve());
        } catch (Impossible e) {
            out.println(-1);
        }
        out.close();
    }

    static class Impossible extends Exception {}

    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        FastScanner(File f) throws IOException { this.in = new FileInputStream(f); }
        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }
        int nextInt() throws IOException {
            int c;
            while ((c = read()) <= ' ') if (c == -1) return Integer.MIN_VALUE;
            int sign = 1;
            if (c == '-') { sign = -1; c = read(); }
            int val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sign;
        }
    }
}