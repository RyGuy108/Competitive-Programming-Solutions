import java.io.*;
import java.util.*;

public class badmilktest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("badmilk.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("badmilk.out")));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken()); // people
        int M = Integer.parseInt(st.nextToken()); // milk types
        int D = Integer.parseInt(st.nextToken()); // drinks
        int S = Integer.parseInt(st.nextToken()); // sicks

        // read drinks: each entry is {person, milk, time}
        int[][] drinks = new int[D][3];
        for (int i = 0; i < D; i++) {
            st = new StringTokenizer(br.readLine());
            drinks[i][0] = Integer.parseInt(st.nextToken());
            drinks[i][1] = Integer.parseInt(st.nextToken());
            drinks[i][2] = Integer.parseInt(st.nextToken());
        }

        // read sick reports: each entry is {person, time}
        int[][] sick = new int[S][2];
        for (int i = 0; i < S; i++) {
            st = new StringTokenizer(br.readLine());
            sick[i][0] = Integer.parseInt(st.nextToken());
            sick[i][1] = Integer.parseInt(st.nextToken());
        }

        int answer = 0;

        // For each milk type, check if it could be the bad milk
        for (int milk = 1; milk <= M; milk++) {
            boolean possible = true;

            // For every sick person, check they drank this milk strictly before they got sick
            for (int i = 0; i < S && possible; i++) {
                int person = sick[i][0];
                int sickTime = sick[i][1];
                boolean drankBefore = false;
                for (int j = 0; j < D; j++) {
                    if (drinks[j][0] == person && drinks[j][1] == milk && drinks[j][2] < sickTime) {
                        drankBefore = true;
                        break;
                    }
                }
                if (!drankBefore) possible = false;
            }

            // If milk can be the bad one, count how many distinct people drank it at any time
            if (possible) {
                boolean[] drank = new boolean[N + 1];
                int count = 0;
                for (int j = 0; j < D; j++) {
                    if (drinks[j][1] == milk) {
                        int p = drinks[j][0];
                        if (!drank[p]) {
                            drank[p] = true;
                            count++;
                        }
                    }
                }
                answer = Math.max(answer, count);
            }
        }

        pw.println(answer);
        pw.close();
        br.close();
    }
} 
