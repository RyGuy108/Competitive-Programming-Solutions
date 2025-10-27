import java.io.*;
import java.util.*;

public class teleport {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("teleport.in"));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        br.close();

        int direct = Math.abs(a - b);
        int viaXY = Math.abs(a - x) + Math.abs(y - b);
        int viaYX = Math.abs(a - y) + Math.abs(x - b);

        int ans = Math.min(direct, Math.min(viaXY, viaYX));

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("teleport.out")));
        pw.println(ans);
        pw.close();
    }
}