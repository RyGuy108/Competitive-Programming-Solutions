import java.io.*;
import java.util.*;

public class shuffle {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("shuffle.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("shuffle.out")));

        int N = Integer.parseInt(br.readLine().trim());
        int[] a = new int[N+1];              // permutation 1..N
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) a[i] = Integer.parseInt(st.nextToken());

        // read final ordering (after 3 shuffles). IDs may be large; keep as strings.
        String[] finalOrder = new String[N+1];
        // final line may contain N tokens possibly across multiple lines, so gather tokens until we have N.
        ArrayList<String> tokens = new ArrayList<>();
        while (tokens.size() < N) {
            String line = br.readLine();
            if (line == null) break;
            StringTokenizer t2 = new StringTokenizer(line);
            while (t2.hasMoreTokens()) tokens.add(t2.nextToken());
        }
        for (int i = 1; i <= N; i++) finalOrder[i] = tokens.get(i-1);

        // compute p^3(k) for each k and set initial[k] = final[ p^3(k) ]
        for (int k = 1; k <= N; k++) {
            int j = a[k];         // p(k)
            j = a[j];             // p^2(k)
            j = a[j];             // p^3(k)
            pw.println(finalOrder[j]);
        }

        pw.close();
        br.close();
    }
}