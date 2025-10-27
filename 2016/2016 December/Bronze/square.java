import java.io.*;
import java.util.*;

public class square {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("square.in"));
        PrintWriter  pw = new PrintWriter(new FileWriter("square.out"));

        int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;

        // Read two rectangles
        for (int i = 0; i < 2; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x1 = Integer.parseInt(st.nextToken());
            int y1 = Integer.parseInt(st.nextToken());
            int x2 = Integer.parseInt(st.nextToken());
            int y2 = Integer.parseInt(st.nextToken());
            minX = Math.min(minX, x1);
            maxX = Math.max(maxX, x2);
            minY = Math.min(minY, y1);
            maxY = Math.max(maxY, y2);
        }

        int side = Math.max(maxX - minX, maxY - minY);
        pw.println(side * side);
        pw.close();
    }
}