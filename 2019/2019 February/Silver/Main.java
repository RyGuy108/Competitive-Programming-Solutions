package Silver;

import java.io.*;
import java.util.*;

public class Main {
  public static void main(String[] args) throws Exception {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int N = Integer.parseInt(br.readLine().trim());
    long[] A = new long[N];
    int idx = 0;
    while (idx < N) {
      String line = br.readLine();
      if (line == null) break;
      StringTokenizer st = new StringTokenizer(line);
      while (st.hasMoreTokens() && idx < N) {
        A[idx++] = Long.parseLong(st.nextToken());
      }
    }
    Arrays.sort(A);
    int answerMin = solveMin(A, N);
    long gap1 = A[N-2] - A[0];
    long gap2 = A[N-1] - A[1];
    long answerMax = Math.max(gap1, gap2) - (N - 2);
    System.out.println(answerMin);
    System.out.println(answerMax);
  }

  static int solveMin(long[] A, int N) {
    if (A[N-2] - A[0] == N-2 && A[N-1] - A[N-2] > 2) return 2;
    if (A[N-1] - A[1] == N-2 && A[1] - A[0] > 2) return 2;
    int j = 0, best = 0;
    for (int i = 0; i < N; i++) {
      if (j < i) j = i;
      while (j + 1 < N && A[j + 1] - A[i] <= N - 1) j++;
      best = Math.max(best, j - i + 1);
    }
    return N - best;
  }
}