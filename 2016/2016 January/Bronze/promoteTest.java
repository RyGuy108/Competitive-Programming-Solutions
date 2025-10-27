import java.io.*;
import java.util.*;

public class promoteTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("promote.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("promote.out")));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int bBefore = Integer.parseInt(st.nextToken());
        int bAfter  = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int sBefore = Integer.parseInt(st.nextToken());
        int sAfter  = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int gBefore = Integer.parseInt(st.nextToken());
        int gAfter  = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int pBefore = Integer.parseInt(st.nextToken());
        int pAfter  = Integer.parseInt(st.nextToken());

        // Let:
        // x = # promoted from bronze -> silver
        // y = # promoted from silver -> gold
        // z = # promoted from gold -> platinum
        //
        // From final counts:
        // pAfter = pBefore + z                          => z = pAfter - pBefore
        // gAfter = gBefore + y - z                     => y = gAfter - gBefore + z
        // sAfter = sBefore + x - y                     => x = sAfter - sBefore + y

        int z = pAfter - pBefore;
        int y = gAfter - gBefore + z;
        int x = sAfter - sBefore + y;

        pw.println(x);
        pw.println(y);
        pw.println(z);

        pw.close();
        br.close();
    }
}