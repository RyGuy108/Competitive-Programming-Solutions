import java.io.*;
import java.util.*;

public class lightson {
    static boolean[][] on;
    static boolean[][] visited;
    static ArrayList<Pair>[][] switches;
    static int[] dx = new int[]{-1, 1, 0, 0};
    static int[] dy = new int[]{0, 0, -1, 1};

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("lightson.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("lightson.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // initialize arrays
        switches = new ArrayList[n][n];
        on = new boolean[n][n];
        visited = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                switches[i][j] = new ArrayList<Pair>();
            }
        }

        // read switches
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            switches[x][y].add(new Pair(a, b));
        }
        br.close();

        // start at (0,0)
        on[0][0] = true;
        search(0, 0);

        // count how many lights are on
        int ret = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (on[i][j]) ret++;
            }
        }

        pw.println(ret);
        pw.close();
    }

    // recursive search/DFS from a visited room (x,y)
    public static void search(int x, int y) {
        if (isVisited(x, y)) return;
        visited[x][y] = true;

        // toggle all switches located in this room
        for (Pair p : switches[x][y]) {
            if (!on[p.x][p.y]) {
                on[p.x][p.y] = true;
                // if the newly lit room has any visited neighbor, we can immediately visit it
                if (hasVisitedNeighbor(p.x, p.y)) {
                    search(p.x, p.y);
                }
            }
        }

        // now attempt to walk to neighboring rooms that are on
        for (int k = 0; k < 4; k++) {
            int nx = x + dx[k];
            int ny = y + dy[k];
            if (isOn(nx, ny) && !isVisited(nx, ny)) {
                search(nx, ny);
            }
        }
    }

    // true iff there exists an adjacent cell that is on AND visited
    public static boolean hasVisitedNeighbor(int x, int y) {
        for (int k = 0; k < 4; k++) {
            int nx = x + dx[k];
            int ny = y + dy[k];
            if (isOn(nx, ny) && isVisited(nx, ny)) return true;
        }
        return false;
    }

    // safe bounds check for "on"
    public static boolean isOn(int x, int y) {
        return x >= 0 && x < on.length && y >= 0 && y < on[x].length && on[x][y];
    }

    // safe bounds check for "visited"
    public static boolean isVisited(int x, int y) {
        return x >= 0 && x < visited.length && y >= 0 && y < visited[x].length && visited[x][y];
    }

    static class Pair {
        int x, y;
        Pair(int x, int y) { this.x = x; this.y = y; }
    }
}