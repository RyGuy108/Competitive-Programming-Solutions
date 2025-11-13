import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String first = br.readLine();
        while (first != null && first.trim().isEmpty()) first = br.readLine();
        if (first == null) return;
        StringTokenizer st = new StringTokenizer(first);
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        List<String> words = new ArrayList<>();
        while (words.size() < N) {
            String line = br.readLine();
            if (line == null) break;
            StringTokenizer st2 = new StringTokenizer(line);
            while (st2.hasMoreTokens() && words.size() < N) words.add(st2.nextToken());
        }
        StringBuilder out = new StringBuilder();
        int w = 0;
        for (String word : words) {
            int len = word.length();
            if (w + len > K) {
                out.append("\n").append(word);
                w = len;
            } else {
                if (w > 0) out.append(" ");
                out.append(word);
                w += len;
            }
        }
        out.append("\n");
        System.out.print(out.toString());
    }
}