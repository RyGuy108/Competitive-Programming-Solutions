import java.io.*;
import java.util.*;

public class mixmilk {
    // pour from index 'from' into index 'to'
    static void pour(int[] cap, int[] milk, int from, int to) {
        int amt = Math.min(milk[from], cap[to] - milk[to]);
        milk[from] -= amt;
        milk[to] += amt;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int[] cap = new int[3];
        int[] milk = new int[3];

        for (int i = 0; i < 3; i++) {
            st = new StringTokenizer(br.readLine());
            cap[i] = Integer.parseInt(st.nextToken());
            milk[i] = Integer.parseInt(st.nextToken());
        }

        // 33 cycles of 3 pours = 99 pours
        for (int i = 0; i < 33; i++) {
            pour(cap, milk, 0, 1);
            pour(cap, milk, 1, 2);
            pour(cap, milk, 2, 0);
        }
        // 100th pour: 1 -> 2
        pour(cap, milk, 0, 1);

        System.out.println(milk[0]);
        System.out.println(milk[1]);
        System.out.println(milk[2]);
    }
}