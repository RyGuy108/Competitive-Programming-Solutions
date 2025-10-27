import java.io.*;
import java.util.*;

public class spainting {
    static final long MOD = 1_000_000_007L;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        long M = Long.parseLong(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        // s[i] = sum_{j=1..i} dp(j), where dp(j) = # sequences length j with no K equal consecutive
        long[] s = new long[N + 1];
        s[0] = 0L;

        // compute s[1..N]
        // for i < K: s[i] = M*s[i-1] + M  (equivalently s[i] = M^i)
        // for i >= K: s[i] = M*s[i-1] - (M-1)*s[i-K]
        for (int i = 1; i <= N; i++) {
            if (i < K) {
                s[i] = ( (M % MOD) * s[i-1] + (M % MOD) ) % MOD;
            } else {
                long term1 = (M % MOD) * s[i-1] % MOD;
                long term2 = ((M - 1) % MOD + MOD) % MOD * s[i-K] % MOD;
                s[i] = (term1 - term2) % MOD;
                if (s[i] < 0) s[i] += MOD;
            }
        }

        long total = modPow(M % MOD, N, MOD);            // M^N % MOD
        long dpN = (s[N] - s[N-1]) % MOD;                // sequences with NO K-equal block
        if (dpN < 0) dpN += MOD;
        long ans = (total - dpN) % MOD;
        if (ans < 0) ans += MOD;

        System.out.println(ans);
    }

    // fast modular exponentiation
    static long modPow(long base, long exp, long mod) {
        long res = 1 % mod;
        base %= mod;
        while (exp > 0) {
            if ((exp & 1) == 1) res = (res * base) % mod;
            base = (base * base) % mod;
            exp >>= 1;
        }
        return res;
    }
}