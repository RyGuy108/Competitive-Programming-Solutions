import java.io.*;
import java.util.*;

public class Main3 {
  public static void main(String[] args) throws Exception {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int N = Integer.parseInt(br.readLine().trim());
    String[] type = new String[N];
    long[] A = new long[N];
    long[] B = new long[N];
    for (int i = 0; i < N; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      type[i] = st.nextToken();
      A[i] = Long.parseLong(st.nextToken());
      B[i] = Long.parseLong(st.nextToken());
    }

    long a = -999_999_999L;
    long b =  999_999_999L;
    for (int i = N - 1; i >= 0; i--) {
      String t = type[i];
      if (t.equals("none")) {
        a = Math.max(a, A[i]);
        b = Math.min(b, B[i]);
      } else if (t.equals("off")) {
        a += A[i];
        b += B[i];
      } else {
        a -= B[i];
        b -= A[i];
        if (a < 0) a = 0;
      }
    }
    System.out.println(a + " " + b);

    a = -999_999_999L;
    b =  999_999_999L;
    for (int i = 0; i < N; i++) {
      String t = type[i];
      if (t.equals("none")) {
        a = Math.max(a, A[i]);
        b = Math.min(b, B[i]);
      } else if (t.equals("on")) {
        a += A[i];
        b += B[i];
      } else {
        a -= B[i];
        b -= A[i];
        if (a < 0) a = 0;
      }
    }
    System.out.println(a + " " + b);
  }
}