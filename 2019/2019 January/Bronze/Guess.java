import java.io.*;
import java.util.*;

public class Guess {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        ArrayList<HashSet<String>> chars = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            String name = st.nextToken();
            int K = Integer.parseInt(st.nextToken());
            HashSet<String> set = new HashSet<>();
            for (int j = 0; j < K; j++) {
                set.add(st.nextToken());
            }
            chars.add(set);
        }
        int best = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                HashSet<String> a = chars.get(i);
                HashSet<String> b = chars.get(j);
                if (a.size() > b.size()) {
                    HashSet<String> tmp = a; a = b; b = tmp;
                }
                int common = 0;
                for (String s : a) if (b.contains(s)) common++;
                if (common > best) best = common;
            }
        }
        System.out.println(best + 1);
    }
}