import java.io.*;
import java.util.*;

public class trappedB {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader("trapped.in"));
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("trapped.out")));
    int n = Integer.parseInt(br.readLine().trim());
    Haybale[] bales = new Haybale[n];
    for (int i = 0; i < n; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      long size = Long.parseLong(st.nextToken());
      long position = Long.parseLong(st.nextToken());
      bales[i] = new Haybale(size, position);
    }

    // sort haybales by location (left to right)
    Arrays.sort(bales);

    long ans = 0L;

    // We only need to check each adjacent gap (i, i+1). If a cow starting anywhere
    // in that gap cannot escape, then the entire gap length contributes to the answer.
    for (int i = 0; i < n - 1; i++) {
      long gapLen = bales[i + 1].position - bales[i].position;
      // if gap length is 0 (shouldn't happen because positions are distinct), skip
      if (gapLen <= 0) continue;

      // start with the immediate adjacent bales as the current blocking pair
      int left = i;
      int right = i + 1;

      // expand outward until either we find an escape (left < 0 or right >= n)
      // or we reach a stable pair where neither boundary bale can be broken
      boolean escaped = false;
      while (true) {
        boolean moved = false;
        long currDist = bales[right].position - bales[left].position;

        // If the current distance is greater than the left bale size, that left bale
        // is breakable (size < dist) by running from the right side — so it doesn't block.
        // Move the left boundary one bale to the left (consider a farther bale as the new blocker).
        if (currDist > bales[left].size) {
          left--;
          moved = true;
        }

        // Symmetrically check the right side.
        if (currDist > bales[right].size) {
          right++;
          moved = true;
        }

        // If we moved off the ends, escape is possible
        if (left < 0 || right >= n) {
          escaped = true;
          break;
        }

        // If we did not move in this iteration, both boundary bales are large enough
        // (size >= currDist) to prevent breaking — the gap is trapped.
        if (!moved) break;

        // Otherwise, continue with new left/right. Loop will terminate because left decreases
        // and right increases monotonically and indices are bounded.
      }

      // if no escape, add the adjacent gap length to the answer
      if (!escaped) ans += gapLen;
    }

    pw.println(ans);
    pw.close();
    br.close();
  }

  static class Haybale implements Comparable<Haybale> {
    public long position, size;
    public Haybale(long sizeIn, long positionIn) {
      size = sizeIn;
      position = positionIn;
    }
    public int compareTo(Haybale h) {
      return Long.compare(this.position, h.position);
    }
  }
}