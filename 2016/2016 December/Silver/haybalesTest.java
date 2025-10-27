import java.io.*;
import java.util.*;

public class haybalesTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("haybales.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("haybales.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());

        long[] pos = new long[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) pos[i] = Long.parseLong(st.nextToken());
        Arrays.sort(pos);

        for (int qi = 0; qi < Q; qi++) {
            st = new StringTokenizer(br.readLine());
            long A = Long.parseLong(st.nextToken());
            long B = Long.parseLong(st.nextToken());
            int lo = lowerBound(pos, A);      // first index >= A
            int hi = upperBound(pos, B);      // first index > B
            pw.println(hi - lo);
        }
        pw.close();
        br.close();
    }

    // first index i such that arr[i] >= key, or arr.length if none
    static int lowerBound(long[] arr, long key) {
        int l = 0, r = arr.length;
        while (l < r) {
            int m = (l + r) >>> 1;
            if (arr[m] >= key) r = m;
            else l = m + 1;
        }
        return l;
    }

    // first index i such that arr[i] > key, or arr.length if none
    static int upperBound(long[] arr, long key) {
        int l = 0, r = arr.length;
        while (l < r) {
            int m = (l + r) >>> 1;
            if (arr[m] > key) r = m;
            else l = m + 1;
        }
        return l;
    }
}