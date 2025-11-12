import java.io.*;
import java.util.*;

/*
 Solution for "evolution" translated to Java.

 Read N sub-populations. Each sub-population lists K characteristics (strings).
 Build, for each characteristic, the set of populations that have it.
 Two characteristics "cross" if there exists at least one population having both,
 at least one having only the first, and at least one having only the second.
 If any pair of characteristics crosses then a proper tree is impossible -> "no".
 Otherwise -> "yes".
*/
public class Main3 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sbAll = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sbAll.append(line).append(' ');
        }
        StringTokenizer st = new StringTokenizer(sbAll.toString());
        if (!st.hasMoreTokens()) return;
        int N = Integer.parseInt(st.nextToken());
        Map<String, Integer> idx = new HashMap<>();
        ArrayList<boolean[]> occurs = new ArrayList<>(); // occurs.get(k)[i] = true if char k in population i

        for (int i = 0; i < N; i++) {
            int K = Integer.parseInt(st.nextToken());
            for (int j = 0; j < K; j++) {
                String s = st.nextToken();
                Integer id = idx.get(s);
                if (id == null) {
                    id = occurs.size();
                    idx.put(s, id);
                    boolean[] arr = new boolean[N];
                    occurs.add(arr);
                }
                occurs.get(id)[i] = true;
            }
        }

        int M = occurs.size();
        boolean ok = true;
        outer:
        for (int a = 0; a < M; a++) {
            for (int b = a + 1; b < M; b++) {
                int A = 0, B = 0, AB = 0;
                boolean[] Aarr = occurs.get(a);
                boolean[] Barr = occurs.get(b);
                for (int i = 0; i < N; i++) {
                    boolean ha = Aarr[i];
                    boolean hb = Barr[i];
                    if (ha && hb) AB++;
                    else if (ha) A++;
                    else if (hb) B++;
                    if (AB > 0 && A > 0 && B > 0) {
                        ok = false;
                        break;
                    }
                }
                if (!ok) break outer;
            }
        }

        System.out.println(ok ? "yes" : "no");
    }
}