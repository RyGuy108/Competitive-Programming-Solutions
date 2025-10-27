import java.io.*;
import java.util.*;

public class getevenTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("geteven.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("geteven.out")));

        int N = Integer.parseInt(br.readLine().trim());
        // variables of interest: B E S I G O M
        // map char -> [evenCount, oddCount]
        Map<Character,long[]> cnt = new HashMap<>();
        for (char ch : new char[]{'B','E','S','I','G','O','M'}) cnt.put(ch, new long[]{0L,0L});

        for (int i = 0; i < N; i++) {
            String line = br.readLine().trim();
            if (line.isEmpty()) { i--; continue; }
            StringTokenizer st = new StringTokenizer(line);
            char var = st.nextToken().charAt(0);
            int val = Integer.parseInt(st.nextToken());
            if (!cnt.containsKey(var)) {
                // variable not relevant to expression (shouldn't happen under problem statement)
                cnt.put(var, new long[]{0L,0L});
            }
            if ((val & 1) == 0) cnt.get(var)[0]++; else cnt.get(var)[1]++;
        }

        // for safety ensure every required variable appears at least once (problem guarantees it)
        for (char ch : new char[]{'B','E','S','I','G','O','M'}) {
            if (!cnt.containsKey(ch)) cnt.put(ch, new long[]{0L,0L});
        }

        // total assignments = product of totals for each variable
        long total = 1L;
        for (char ch : new char[]{'B','E','S','I','G','O','M'}) {
            long ev = cnt.get(ch)[0];
            long od = cnt.get(ch)[1];
            long tot = ev + od;
            total = total * tot;
        }

        // Count assignments where all three factors are odd:
        // Conditions:
        //   (B xor I) == 1  -> waysBI = evenB*oddI + oddB*evenI
        //   (G+S+E) %2 ==1  -> waysGSE = sum of cases with 1 or 3 odd among G,S,E
        //   M must be odd: waysM = oddM
        //   O is free: waysO = totalO
        long evenB = cnt.get('B')[0], oddB = cnt.get('B')[1];
        long evenI = cnt.get('I')[0], oddI = cnt.get('I')[1];
        long evenG = cnt.get('G')[0], oddG = cnt.get('G')[1];
        long evenS = cnt.get('S')[0], oddS = cnt.get('S')[1];
        long evenE = cnt.get('E')[0], oddE = cnt.get('E')[1];
        long evenO = cnt.get('O')[0], oddO = cnt.get('O')[1];
        long evenM = cnt.get('M')[0], oddM = cnt.get('M')[1];

        long waysBI = evenB * oddI + oddB * evenI;

        // ways G+S+E ≡ 1 : exactly one odd OR all three odd
        long waysGSE = 0;
        waysGSE += oddG * evenS * evenE;
        waysGSE += evenG * oddS * evenE;
        waysGSE += evenG * evenS * oddE;
        waysGSE += oddG * oddS * oddE; // all three odd

        long waysM = oddM;
        long waysO = evenO + oddO;

        long allOdd = waysBI * waysGSE * waysM * waysO;

        long result = total - allOdd;
        pw.println(result);

        pw.close();
        br.close();
    }
}