package Silver;

import java.io.*;
import java.util.*;

public class Main2 {
  public static void main(String[] args) throws Exception {
    FastScanner fs = new FastScanner(System.in);
    int n = fs.nextInt();
    int k = fs.nextInt();
    int MAX = 1000;
    int[][] dp = new int[MAX+1+1][MAX+1+1];
    for (int t=0;t<n;t++) {
      int a = fs.nextInt();
      int b = fs.nextInt();
      int c = fs.nextInt();
      int d = fs.nextInt();
      dp[a][b] += 1;
      dp[a][d] -= 1;
      dp[c][b] -= 1;
      dp[c][d] += 1;
    }
    int ans = 0;
    for (int i=0;i<=MAX;i++) {
      for (int j=0;j<=MAX;j++) {
        if (i>0) dp[i][j] += dp[i-1][j];
        if (j>0) dp[i][j] += dp[i][j-1];
        if (i>0 && j>0) dp[i][j] -= dp[i-1][j-1];
        if (i<MAX && j<MAX && dp[i][j] == k) ans++;
      }
    }
    System.out.println(ans);
  }

  static class FastScanner {
    BufferedInputStream in;
    byte[] buffer = new byte[1<<16];
    int ptr=0, len=0;
    FastScanner(InputStream is) { in = new BufferedInputStream(is); }
    int read() throws IOException {
      if (ptr>=len) { len = in.read(buffer); ptr=0; if (len<=0) return -1; }
      return buffer[ptr++];
    }
    int nextInt() throws IOException {
      int c, s=1, x=0;
      do { c = read(); if (c==-1) return Integer.MIN_VALUE; } while (c<=32);
      if (c=='-') { s=-1; c=read(); }
      while (c>32) { x = x*10 + (c-'0'); c = read(); }
      return x*s;
    }
  }
}