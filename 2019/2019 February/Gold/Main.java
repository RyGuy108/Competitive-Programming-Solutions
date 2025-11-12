package Gold;

import java.io.*;
import java.util.*;

public class Main {
  public static void main(String[] args) throws Exception {
    FastScanner fs = new FastScanner(System.in);
    int n = fs.nextInt();
    int[] base = new int[n+1];
    ArrayDeque<Integer>[] items = new ArrayDeque[n+1];
    for (int i=0;i<=n;i++) items[i]=new ArrayDeque<>();
    int placed = 0;
    int ans = n;
    for (int i=0;i<n;i++) {
      int x = fs.nextInt();
      if (x < placed) {
        ans = i;
        break;
      }
      for (int j = x; j > 0 && base[j] == 0; j--) base[j] = x;
      int idx = base[x];
      ArrayDeque<Integer> stk = items[idx];
      while (!stk.isEmpty() && stk.peekLast() < x) {
        placed = stk.peekLast();
        stk.removeLast();
      }
      stk.addLast(x);
    }
    System.out.println(ans);
  }

  static class FastScanner {
    private final InputStream in;
    private final byte[] buffer = new byte[1 << 16];
    private int ptr = 0, len = 0;
    FastScanner(InputStream is) { in = is; }
    private int read() throws IOException {
      if (ptr >= len) {
        len = in.read(buffer);
        ptr = 0;
        if (len <= 0) return -1;
      }
      return buffer[ptr++];
    }
    int nextInt() throws IOException {
      int c;
      while ((c = read()) <= ' ') if (c == -1) return -1;
      int sign = 1;
      if (c == '-') { sign = -1; c = read(); }
      int val = 0;
      while (c > ' ') {
        val = val * 10 + (c - '0');
        c = read();
      }
      return val * sign;
    }
  }
}