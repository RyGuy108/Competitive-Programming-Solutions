import java.io.*;
import java.util.*;

public class odometer {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        while (line != null && line.trim().isEmpty()) line = br.readLine();
        if (line == null) return;
        StringTokenizer st = new StringTokenizer(line);
        long X = Long.parseLong(st.nextToken());
        long Y;
        if (st.hasMoreTokens()) {
            Y = Long.parseLong(st.nextToken());
        } else {
            // maybe second number on next line
            Y = Long.parseLong(br.readLine().trim());
        }

        int result = 0;

        // try all sizes from 3 up to 17 (as in the C++ solution)
        for (int sz = 3; sz <= 17; sz++) {
            // majority digit d0
            for (int d0 = 0; d0 <= 9; d0++) {
                // build default string filled with d0
                char[] s = new char[sz];
                Arrays.fill(s, (char)('0' + d0));

                // the single different digit d1
                for (int d1 = 0; d1 <= 9; d1++) {
                    if (d1 == d0) continue;

                    // put d1 at each position i
                    for (int i = 0; i < sz; i++) {
                        s[i] = (char)('0' + d1);

                        // skip leading zero numbers
                        if (s[0] != '0') {
                            long num = Long.parseLong(new String(s));
                            if (num >= X && num <= Y) result++;
                        }

                        // restore
                        s[i] = (char)('0' + d0);
                    }
                }
            }
        }

        System.out.println(result);
    }
}