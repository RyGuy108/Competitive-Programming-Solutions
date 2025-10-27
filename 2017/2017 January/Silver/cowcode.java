import java.io.*;
import java.util.*;

public class cowcode {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("cowcode.in"));
        String line = br.readLine().trim();
        StringTokenizer st = new StringTokenizer(line);
        String s = st.nextToken();
        long N = Long.parseLong(st.nextToken()); // 1-based N
        br.close();

        char ans = getChar(s, N - 1); // convert to 0-based index
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cowcode.out")));
        pw.println(ans);
        pw.close();
    }

    static char getChar(String s, long index) {
        int seedLen = s.length();
        // find smallest total length (power-of-two multiple of seedLen) >= index+1
        long len = seedLen;
        while (len <= index) len *= 2;

        // reduce index until it falls inside the original seed string
        while (index >= seedLen) {
            long half = len / 2;
            if (index < half) {
                // in the first half: just reduce len
                len = half;
            } else {
                // in the rotated second half:
                // rotated[j] corresponds to original[(j-1) mod half]
                // j = index - half  => map back to (index - 1) % half
                index = (index - 1) % half;
                len = half;
            }
        }
        return s.charAt((int) index);
    }
}