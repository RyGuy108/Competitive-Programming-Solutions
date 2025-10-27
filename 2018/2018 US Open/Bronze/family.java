import java.io.*;
import java.util.*;

public class family {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("family.in"));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        String bessie = st.nextToken();
        String elsie = st.nextToken();

        // map: daughter -> mother
        Map<String,String> mom = new HashMap<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            String m = st.nextToken();
            String d = st.nextToken();
            mom.put(d, m);
        }
        br.close();

        // Helper: is anc an ancestor of desc? return generations (0 means same),
        // or -1 if not ancestor.
        class Helpers {
            int isAncestor(String anc, String desc) {
                int count = 0;
                String cur = desc;
                while (cur != null) {
                    if (cur.equals(anc)) return count;
                    cur = mom.get(cur);
                    count++;
                }
                return -1;
            }
        }
        Helpers H = new Helpers();

        // climb from Bessie up to find a common ancestor with Elsie
        String cur = bessie;
        int b = 0; // distance from Bessie up to current ancestor
        while (cur != null) {
            int a = H.isAncestor(cur, elsie);
            if (a != -1) {
                // found common ancestor 'cur'
                // a = generations from cur to Elsie
                // b = generations from Bessie to cur
                // handle relations
                if (a == 1 && b == 1) {
                    writeOut("SIBLINGS");
                    return;
                }
                if (a > 1 && b > 1) {
                    writeOut("COUSINS");
                    return;
                }
                // normalize so that a <= b by possibly swapping roles
                String nameA = elsie;
                String nameB = bessie;
                int aa = a;
                int bb = b;
                if (a > b) {
                    // swap roles: print relationship the other way
                    String tmpS = nameA; nameA = nameB; nameB = tmpS;
                    int tmp = aa; aa = bb; bb = tmp;
                }
                // now aa <= bb
                // output e.g. "Elsie is the great-...-grand-mother of Bessie" or "aunt"
                StringBuilder sb = new StringBuilder();
                sb.append(nameA);
                sb.append(" is the ");
                for (int i = 0; i < bb - 2; i++) sb.append("great-");
                if (bb > 1 && aa == 0) sb.append("grand-");
                if (aa == 0) sb.append("mother");
                else sb.append("aunt");
                sb.append(" of ");
                sb.append(nameB);
                writeOut(sb.toString());
                return;
            }
            // move up one generation for Bessie's side
            cur = mom.get(cur);
            b++;
        }

        // If we finished climbing and found no common ancestor
        writeOut("NOT RELATED");
    }

    static void writeOut(String s) throws IOException {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("family.out")));
        pw.println(s);
        pw.close();
    }
}