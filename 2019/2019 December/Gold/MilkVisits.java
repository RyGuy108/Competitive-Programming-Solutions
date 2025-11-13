import java.io.*;
import java.util.*;

public class MilkVisits {
    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;
        FastScanner(InputStream is){ br = new BufferedReader(new InputStreamReader(is)); }
        String next() throws IOException {
            while (st==null || !st.hasMoreTokens()) {
                String line = br.readLine();
                if (line == null) return null;
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }
        int nextInt() throws IOException { return Integer.parseInt(next()); }
    }

    static int N, M;
    static char[] type; 
    static ArrayList<Integer>[] adj;
    static int[][] queries;
    static int[] qType;    
    static ArrayList<Integer>[] todo;
    static int[] tin, tout;
    static int timer;

    public static void main(String[] args) throws Exception {
        FastScanner fs;
        PrintWriter out;
        try {
            fs = new FastScanner(new FileInputStream("milkvisits.in"));
            out = new PrintWriter(new FileOutputStream("milkvisits.out"));
        } catch (FileNotFoundException e) {
            fs = new FastScanner(System.in);
            out = new PrintWriter(System.out);
        }

        N = fs.nextInt();
        M = fs.nextInt();
        type = new char[N+1];
        String s = fs.next();
        for (int i=1;i<=N;i++) type[i] = s.charAt(i-1);

        adj = new ArrayList[N+1];
        for (int i=1;i<=N;i++) adj[i] = new ArrayList<>();

        for (int i=0;i<N-1;i++){
            int a = fs.nextInt();
            int b = fs.nextInt();
            adj[a].add(b);
            adj[b].add(a);
        }

        queries = new int[M][2];
        qType = new int[M];
        todo = new ArrayList[N+1];
        for (int i=1;i<=N;i++) todo[i] = new ArrayList<>();

        for (int i=0;i<M;i++){
            int a = fs.nextInt();
            int b = fs.nextInt();
            String t = fs.next();
            queries[i][0] = a;
            queries[i][1] = b;
            qType[i] = t.charAt(0); // store 'G' or 'H'
            todo[a].add(i);
            todo[b].add(i);
        }

        tin = new int[N+1];
        tout = new int[N+1];
        computeEulerRanges();


        // stor mapping: use HashMap<Character, ArrayList<int[]>> or array indexed by char.
        HashMap<Integer, ArrayList<int[]>> stor = new HashMap<>();
        ArrayList<Integer> ord = new ArrayList<>();
        boolean[] ok = new boolean[M];

        // iterative DFS2: push entry frames
        class Frame { int node, parent; boolean entered; Frame(int n,int p,boolean e){node=n;parent=p;entered=e;} }
        Deque<Frame> stack = new ArrayDeque<>();
        stack.push(new Frame(1, 0, true));

        while (!stack.isEmpty()){
            Frame fr = stack.pop();
            int node = fr.node;

            if (fr.entered) {
                // pre-order actions
                // push exit frame
                stack.push(new Frame(node, fr.parent, false));

                // push stor entry for this node's type
                int ch = (int) type[node];
                stor.computeIfAbsent(ch, k->new ArrayList<>()).add(new int[]{node, ord.size()});
                ord.add(node);

                // process queries that have this node as an endpoint
                for (int qi : todo[node]) {
                    ArrayList<int[]> stList = stor.get(qType[qi]);
                    if (stList != null && stList.size() > 0) {
                        int[] last = stList.get(stList.size()-1);
                        int yNode = last[0];
                        int yPos = last[1];
                        if (yNode == node) {
                            ok[qi] = true;
                        } else {
                            // compute the other endpoint of this query (the one that is not 'node')
                            int other = queries[qi][0] + queries[qi][1] - node;
                            // Y = ord[yPos+1]
                            int Y = ord.get(yPos + 1);
                            // If Y is ancestor of other, then y is not LCA => not ok here.
                            // Otherwise, y is LCA => ok
                            if (!isAncestor(Y, other)) ok[qi] = true;
                        }
                    }
                }

                // push children frames in reverse so that they are processed in original order
                ArrayList<Integer> children = adj[node];
                for (int i = children.size()-1; i>=0; --i) {
                    int c = children.get(i);
                    if (c == fr.parent) continue;
                    stack.push(new Frame(c, node, true));
                }
            } else {
                // post-order actions: pop stor and ord
                int ch = (int) type[node];
                ArrayList<int[]> stList = stor.get(ch);
                // remove last entry
                if (stList != null && !stList.isEmpty()) stList.remove(stList.size()-1);
                // pop ord
                ord.remove(ord.size()-1);
            }
        }

        // output answers: 1 if ok else 0 for each query in order
        StringBuilder sb = new StringBuilder(M);
        for (int i=0;i<M;i++) sb.append(ok[i] ? '1' : '0');
        out.println(sb.toString());
        out.flush();
        out.close();
    }

    static void computeEulerRanges() {
        tin = new int[N+1];
        tout = new int[N+1];
        int[] iter = new int[N+1];
        int[] parent = new int[N+1];
        Deque<Integer> st = new ArrayDeque<>();
        st.push(1);
        parent[1] = 0;
        timer = 0;
        while (!st.isEmpty()) {
            int v = st.peek();
            if (iter[v] == 0) {
                tin[v] = timer++;
            }
            if (iter[v] < adj[v].size()) {
                int to = adj[v].get(iter[v]++);
                if (to == parent[v]) continue;
                parent[to] = v;
                st.push(to);
            } else {
                st.pop();
                tout[v] = timer-1;
            }
        }
    }

    static boolean isAncestor(int a, int b) {
        // a is ancestor of b if tin[a] <= tin[b] and tout[b] <= tout[a]
        return tin[a] <= tin[b] && tout[b] <= tout[a];
    }
}