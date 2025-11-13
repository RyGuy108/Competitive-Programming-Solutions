import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        long N = Long.parseLong(br.readLine().trim());
        int[] base = {1,2,4,7,8,11,13,14};
        long group = (N - 1) / 8;
        int idx = (int)((N - 1) % 8);
        long ans = base[idx] + 15L * group;
        System.out.println(ans);
    }
}