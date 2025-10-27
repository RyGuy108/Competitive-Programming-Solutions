import java.io.*;
import java.util.*;

public class blist {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("blist.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("blist.out")));
        
        int N = Integer.parseInt(br.readLine().trim());
        // times guaranteed in 1..1000
        int MAXT = 1000;
        
        int[] S = new int[N+1];
        int[] T = new int[N+1];
        int[] B = new int[N+1];
        // startAt[t] = index of cow that starts at time t (0 if none)
        // finishAt[t] = index of cow that finishes at time t (0 if none)
        int[] startAt = new int[MAXT+1];
        int[] finishAt = new int[MAXT+1];
        
        for (int i = 1; i <= N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            S[i] = Integer.parseInt(st.nextToken());
            T[i] = Integer.parseInt(st.nextToken());
            B[i] = Integer.parseInt(st.nextToken());
            startAt[S[i]] = i;
            finishAt[T[i]] = i;
        }
        br.close();
        
        int needed = 0;
        int current = 0;
        // sweep time from 1..MAXT
        for (int t = 1; t <= MAXT; t++) {
            if (startAt[t] != 0) {
                current += B[startAt[t]];
            }
            needed = Math.max(needed, current);
            if (finishAt[t] != 0) {
                current -= B[finishAt[t]];
            }
        }
        
        pw.println(needed);
        pw.close();
    }
}