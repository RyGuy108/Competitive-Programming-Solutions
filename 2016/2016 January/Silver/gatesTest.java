import java.io.*;
import java.util.*;

/*
 * gatesTest.java
 *
 * Count distinct vertices visited and distinct (undirected) edges used.
 * Answer = edges - vertices + 1
 */
public class gatesTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("gates.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("gates.out")));

        int N = Integer.parseInt(br.readLine().trim());
        String path = br.readLine().trim();

        // Sets to store unique vertices and unique undirected edges
        HashSet<String> vertices = new HashSet<>();
        HashSet<String> edges = new HashSet<>();

        // start at origin (0,0)
        int x = 0, y = 0;
        vertices.add(keyVertex(x, y));

        for (int i = 0; i < N; i++) {
            int nx = x, ny = y;
            char c = path.charAt(i);
            if (c == 'N') ny = y + 1;
            else if (c == 'S') ny = y - 1;
            else if (c == 'E') nx = x + 1;
            else if (c == 'W') nx = x - 1;
            else throw new IllegalArgumentException("Invalid direction: " + c);

            // add the new vertex
            vertices.add(keyVertex(nx, ny));

            // create an undirected normalized edge key from (x,y) to (nx,ny)
            String ekey = keyEdge(x, y, nx, ny);
            edges.add(ekey);

            // move to next position
            x = nx; y = ny;
        }

        int V = vertices.size();
        int E = edges.size();
        int result = E - V + 1;
        pw.println(result);

        pw.close();
        br.close();
    }

    // vertex key "x,y"
    private static String keyVertex(int x, int y) {
        return x + "," + y;
    }

    // undirected edge key normalized so smaller endpoint comes first lexicographically
    private static String keyEdge(int x1, int y1, int x2, int y2) {
        // compare endpoints, return "xA,yA:xB,yB" where (xA,yA) <= (xB,yB)
        if (x1 < x2 || (x1 == x2 && y1 <= y2)) {
            return x1 + "," + y1 + ":" + x2 + "," + y2;
        } else {
            return x2 + "," + y2 + ":" + x1 + "," + y1;
        }
    }
}