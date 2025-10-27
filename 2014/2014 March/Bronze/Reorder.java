import java.io.*;
import java.util.StringTokenizer;

public class Reorder {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("reorder.in"));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int[] A = new int[N + 1];
        int[] B = new int[N + 1];
        int[] whereInB = new int[N + 1];
        boolean[] visited = new boolean[N + 1];

        // read A
        for (int i = 1; i <= N; i++) {
            if (!st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
            A[i] = Integer.parseInt(st.nextToken());
        }

        // read B (may continue on same line or next lines)
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            if (!st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
            B[i] = Integer.parseInt(st.nextToken());
            whereInB[B[i]] = i; // position of cow numbered B[i] in array B
        }
        br.close();

        int numCycles = 0;
        int longest = -1;

        for (int i = 1; i <= N; i++) {
            // only consider positions where A and B differ and not yet visited
            if (A[i] != B[i] && !visited[i]) {
                int len = traceCycle(i, A, whereInB, visited);
                if (len > 0) {
                    numCycles++;
                    if (len > longest) longest = len;
                }
            }
        }

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("reorder.out")));
        pw.println(numCycles + " " + longest);
        pw.close();
    }

    // traverse cycle starting at 'start' and mark visited positions
    private static int traceCycle(int start, int[] A, int[] whereInB, boolean[] visited) {
        int count = 0;
        int i = start;
        do {
            visited[i] = true;
            // next position is where the cow that currently sits at A[i] should go in B
            i = whereInB[A[i]];
            count++;
        } while (i != start);
        return count;
    }
}