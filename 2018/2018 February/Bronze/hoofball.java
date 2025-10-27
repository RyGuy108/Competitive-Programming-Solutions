import java.io.*;
import java.util.*;

public class hoofball {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("hoofball.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("hoofball.out")));

        int N = Integer.parseInt(br.readLine().trim());
        int[] x = new int[N];
        for (int i = 0; i < N; i++) x[i] = Integer.parseInt(br.readLine().trim());

        // compute target(i) for each cow i
        int[] target = new int[N];
        for (int i = 0; i < N; i++) {
            int leftIdx = -1, rightIdx = -1;
            int leftDist = Integer.MAX_VALUE, rightDist = Integer.MAX_VALUE;
            for (int j = 0; j < N; j++) {
                if (j == i) continue;
                if (x[j] < x[i]) {
                    int d = x[i] - x[j];
                    if (d < leftDist) {
                        leftDist = d;
                        leftIdx = j;
                    }
                } else if (x[j] > x[i]) {
                    int d = x[j] - x[i];
                    if (d < rightDist) {
                        rightDist = d;
                        rightIdx = j;
                    }
                }
            }
            // tie breaks to the left
            if (leftDist <= rightDist) target[i] = leftIdx;
            else target[i] = rightIdx;
        }

        // passto[j] = how many cows pass to cow j
        int[] passto = new int[N];
        for (int i = 0; i < N; i++) {
            int t = target[i];
            if (t != -1) passto[t]++;
        }

        int answer = 0;
        // +1 for each cow that nobody passes to
        for (int i = 0; i < N; i++) {
            if (passto[i] == 0) answer++;
        }

        // +1 for each isolated mutual pair i<->j where both have passto==1
        for (int i = 0; i < N; i++) {
            int j = target[i];
            if (j > i && j != -1) { // consider each pair once
                if (target[j] == i && passto[i] == 1 && passto[j] == 1) {
                    answer++;
                }
            }
        }

        pw.println(answer);
        pw.close();
        br.close();
    }
}