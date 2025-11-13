import java.io.*;
import java.util.*;

public class Main2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine().trim());
        String S = br.readLine().trim();
        for (int len = 1; len <= N; len++) {
            HashSet<String> seen = new HashSet<>();
            boolean dup = false;
            for (int i = 0; i + len <= N; i++) {
                String sub = S.substring(i, i + len);
                if (!seen.add(sub)) { dup = true; break; }
            }
            if (!dup) {
                System.out.println(len);
                return;
            }
        }
    }
}
