package Silver;

import java.io.*;
import java.util.*;

public class Main3 {
  public static void main(String[] args) throws Exception {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken());
    int M = Integer.parseInt(st.nextToken());
    ArrayList<Integer>[] Snbr = new ArrayList[N+1];
    ArrayList<Integer>[] Dnbr = new ArrayList[N+1];
    for (int i=1; i<=N; i++){ Snbr[i]=new ArrayList<>(); Dnbr[i]=new ArrayList<>(); }
    for (int i=0; i<M; i++){
      st = new StringTokenizer(br.readLine());
      String s = st.nextToken();
      int a = Integer.parseInt(st.nextToken());
      int b = Integer.parseInt(st.nextToken());
      if (s.equals("S")) { Snbr[a].add(b); Snbr[b].add(a); }
      else { Dnbr[a].add(b); Dnbr[b].add(a); }
    }
    int[] L = new int[N+1];
    boolean impossible = false;
    int components = 0;
    ArrayDeque<Integer> stack = new ArrayDeque<>();
    for (int i=1; i<=N; i++) {
      if (L[i]==0) {
        components++;
        L[i]=1;
        stack.push(i);
        while (!stack.isEmpty()) {
          int u = stack.pop();
          int v = L[u];
          for (int n : Snbr[u]) {
            if (L[n]==3-v) { impossible = true; break; }
            if (L[n]==0) { L[n]=v; stack.push(n); }
          }
          if (impossible) break;
          for (int n : Dnbr[u]) {
            if (L[n]==v) { impossible = true; break; }
            if (L[n]==0) { L[n]=3-v; stack.push(n); }
          }
          if (impossible) break;
        }
        if (impossible) break;
      }
    }
    StringBuilder out = new StringBuilder();
    if (impossible) out.append("0\n");
    else {
      out.append("1");
      for (int k=0; k<components; k++) out.append("0");
      out.append("\n");
    }
    System.out.print(out.toString());
  }
}