import java.io.*;
import java.util.*;

public class billboard {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("billboard.in"));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int lx1 = Integer.parseInt(st.nextToken());
        int ly1 = Integer.parseInt(st.nextToken());
        int lx2 = Integer.parseInt(st.nextToken());
        int ly2 = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int fx1 = Integer.parseInt(st.nextToken());
        int fy1 = Integer.parseInt(st.nextToken());
        int fx2 = Integer.parseInt(st.nextToken());
        int fy2 = Integer.parseInt(st.nextToken());

        br.close();

        int lawnWidth = lx2 - lx1;
        int lawnHeight = ly2 - ly1;
        int lawnArea = lawnWidth * lawnHeight;

        // If feed completely covers lawn -> 0
        if (fx1 <= lx1 && fx2 >= lx2 && fy1 <= ly1 && fy2 >= ly2) {
            System.out.println(0);
            return;
        }

        int answer = lawnArea; // default: must cover the whole lawn

        // If feed covers full horizontal span of lawn
        if (fx1 <= lx1 && fx2 >= lx2) {
            // feed touches or goes below lawn bottom -> maybe top remains
            if (fy1 <= ly1) {
                // top remaining height = max(0, ly2 - fy2)
                int remH = Math.max(0, ly2 - fy2);
                answer = Math.min(answer, lawnWidth * remH);
            }
            // feed touches or goes above lawn top -> maybe bottom remains
            if (fy2 >= ly2) {
                int remH = Math.max(0, fy1 - ly1);
                answer = Math.min(answer, lawnWidth * remH);
            }
            // if feed is strictly inside vertically (fy1 > ly1 && fy2 < ly2),
            // it splits lawn into two pieces; answer remains lawnArea.
        }

        // If feed covers full vertical span of lawn
        if (fy1 <= ly1 && fy2 >= ly2) {
            // feed touches or goes left of lawn left -> right remaining
            if (fx1 <= lx1) {
                int remW = Math.max(0, lx2 - fx2);
                answer = Math.min(answer, lawnHeight * remW);
            }
            // feed touches or goes right of lawn right -> left remaining
            if (fx2 >= lx2) {
                int remW = Math.max(0, fx1 - lx1);
                answer = Math.min(answer, lawnHeight * remW);
            }
            // if feed is strictly inside horizontally, it splits lawn into two pieces;
            // answer remains lawnArea.
        }

        System.out.println(answer);
    }
}