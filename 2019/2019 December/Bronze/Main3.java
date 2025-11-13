import java.io.*;
import java.util.*;

public class Main3 {
    static List<String> cows = Arrays.asList("Beatrice","Belinda","Bella","Bessie","Betsy","Blue","Buttercup","Sue");
    static List<String[]> constraints = new ArrayList<>();
    static String[] order = new String[8];
    static boolean[] used = new boolean[8];

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine().trim());
        for (int i=0;i<N;i++) {
            String line = br.readLine();
            String[] toks = line.split("\\s+");
            String a = toks[0];
            String b = toks[toks.length-1];
            constraints.add(new String[]{a,b});
        }
        List<String> list = new ArrayList<>(cows);
        Collections.sort(list);
        backtrack(0, list);
    }

    static void backtrack(int idx, List<String> list) {
        if (idx==8) {
            if (valid()) {
                StringBuilder sb = new StringBuilder();
                for (String s : order) sb.append(s).append('\n');
                System.out.print(sb.toString());
                System.exit(0);
            }
            return;
        }
        for (int i=0;i<8;i++) {
            if (used[i]) continue;
            used[i]=true;
            order[idx]=list.get(i);
            backtrack(idx+1, list);
            used[i]=false;
        }
    }

    static boolean valid() {
        Map<String,Integer> pos = new HashMap<>();
        for (int i=0;i<8;i++) pos.put(order[i], i);
        for (String[] c : constraints) {
            int p = pos.get(c[0]);
            int q = pos.get(c[1]);
            if (Math.abs(p-q) != 1) return false;
        }
        return true;
    }
}