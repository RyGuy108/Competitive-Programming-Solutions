import java.io.*;
import java.util.*;

public class lifeguards {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("lifeguards.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("lifeguards.out")));
        
        // read in the information about the lifeguards
        int n = Integer.parseInt(br.readLine().trim());
        int[] start = new int[n];
        int[] end = new int[n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            start[i] = Integer.parseInt(st.nextToken());
            end[i] = Integer.parseInt(st.nextToken());
        }
        br.close();
        
        // number of lifeguards covering each unit time in [0,1000)
        int MAXT = 1000;
        int[] numCover = new int[MAXT];
        for (int i = 0; i < n; i++) {
            for (int t = start[i]; t < end[i]; t++) {
                numCover[t]++;
            }
        }
        
        int maxCover = 0;
        // try firing each lifeguard i
        for (int i = 0; i < n; i++) {
            // fire i temporarily
            for (int t = start[i]; t < end[i]; t++) {
                numCover[t]--;
            }
            // count how many time units are still covered
            int covered = 0;
            for (int t = 0; t < MAXT; t++) {
                if (numCover[t] > 0) covered++;
            }
            maxCover = Math.max(maxCover, covered);
            // revert the firing
            for (int t = start[i]; t < end[i]; t++) {
                numCover[t]++;
            }
        }
        
        pw.println(maxCover);
        pw.close();
    }
}