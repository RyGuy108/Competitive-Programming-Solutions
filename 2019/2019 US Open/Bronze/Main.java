import java.io.*;
import java.util.*;
public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int barn_i=-1,barn_j=-1,rock_i=-1,rock_j=-1,lake_i=-1,lake_j=-1;
        for (int i=0;i<10;i++) {
            String s = br.readLine();
            if (s == null) return;
            while (s.length() < 10) s += " ";
            for (int j=0;j<10 && j<s.length(); j++) {
                char c = s.charAt(j);
                if (c == 'B') { barn_i = i; barn_j = j; }
                if (c == 'R') { rock_i = i; rock_j = j; }
                if (c == 'L') { lake_i = i; lake_j = j; }
            }
        }
        int dist_br = Math.abs(barn_i - rock_i) + Math.abs(barn_j - rock_j);
        int dist_bl = Math.abs(barn_i - lake_i) + Math.abs(barn_j - lake_j);
        int dist_rl = Math.abs(rock_i - lake_i) + Math.abs(rock_j - lake_j);
        if ((barn_i == lake_i || barn_j == lake_j) && dist_bl == dist_br + dist_rl)
            System.out.println(dist_bl + 1);
        else
            System.out.println(dist_bl - 1);
    }
}