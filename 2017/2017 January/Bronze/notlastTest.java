import java.io.*;
import java.util.*;

public class notlastTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("notlast.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("notlast.out")));

        int N = Integer.parseInt(br.readLine().trim());

        String[] names = {"Bessie","Elsie","Daisy","Gertie","Annabelle","Maggie","Henrietta"};
        Map<String,Integer> idx = new HashMap<>();
        for (int i = 0; i < names.length; i++) idx.put(names[i], i);

        int[] total = new int[names.length]; // totals default to 0

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String cow = st.nextToken();
            int amt = Integer.parseInt(st.nextToken());
            total[idx.get(cow)] += amt;
        }

        // find minimum total M among all seven cows
        int min = Integer.MAX_VALUE;
        for (int t : total) min = Math.min(min, t);

        // find the smallest total strictly greater than min (the "second-smallest")
        int second = Integer.MAX_VALUE;
        for (int t : total) {
            if (t > min) second = Math.min(second, t);
        }

        // If no cow has total > min, or several cows tie for the second value, print "Tie".
        if (second == Integer.MAX_VALUE) {
            pw.println("Tie");
            pw.close();
            br.close();
            return;
        }

        int count = 0;
        int which = -1;
        for (int i = 0; i < total.length; i++) {
            if (total[i] == second) {
                count++;
                which = i;
            }
        }

        if (count == 1) pw.println(names[which]);
        else pw.println("Tie");

        pw.close();
        br.close();
    }
}