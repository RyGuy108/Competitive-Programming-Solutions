import java.io.*;
import java.util.*;

public class citystateTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("citystate.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("citystate.out")));
        int N = Integer.parseInt(br.readLine().trim());

        HashMap<String, Integer> map = new HashMap<>(N * 2);

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String city = st.nextToken();
            String state = st.nextToken();
            String prefix = city.substring(0, 2);
            String key = prefix + state; // 4-char key: first two letters of city + state
            map.put(key, map.getOrDefault(key, 0) + 1);
        }

        long total = 0;
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            String key = e.getKey();        // key = prefix(stateA) + stateA
            String a = key.substring(0, 2);
            String b = key.substring(2, 4);
            if (a.equals(b)) continue;      // states must be different
            String rev = b + a;             // reverse key = prefix(stateB) + stateB
            Integer cntRev = map.get(rev);
            if (cntRev != null) {
                total += (long) e.getValue() * cntRev;
            }
        }

        // each unordered pair counted twice (once as (a,b) and once as (b,a))
        pw.println(total / 2);
        pw.close();
        br.close();
    }
}