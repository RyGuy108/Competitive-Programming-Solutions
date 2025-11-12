import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int[] x = {a,b,c};
        Arrays.sort(x);
        a = x[0]; b = x[1]; c = x[2];
        if (c == a + 2) System.out.println(0);
        else if (b == a + 2 || c == b + 2) System.out.println(1);
        else System.out.println(2);
        System.out.println(Math.max(b - a, c - b) - 1);
    }
}