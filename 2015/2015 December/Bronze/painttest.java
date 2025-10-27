import java.io.*;
import java.util.*;

public class painttest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("paint.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("paint.out")));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int c = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());

        // length of each interval
        int len1 = b - a;
        int len2 = d - c;

        // overlap length (if any)
        int overlap = Math.max(0, Math.min(b, d) - Math.max(a, c));

        // union length = sum of lengths - overlap
        int result = len1 + len2 - overlap;

        pw.println(result);
        pw.close();
        br.close();
    }
}