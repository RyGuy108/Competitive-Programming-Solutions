import java.io.*;
import java.util.*;

public class rental {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("rental.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("rental.out")));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());

        // cows' daily milk
        int[] milkProduced = new int[n];
        for (int i = 0; i < n; i++) milkProduced[i] = Integer.parseInt(br.readLine());
        // sort descending: process highest producers first for selling milk
        sortDesc(milkProduced);

        // shops: (quantity, price) sorted by highest price first
        Shop[] shops = new Shop[m];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int q = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());
            shops[i] = new Shop(q, p);
        }
        Arrays.sort(shops); // by price desc

        // maxProfit[i] = best revenue using milk from first i highest-producing cows (no rentals yet)
        long[] maxProfit = new long[n + 1];
        {
            int shopIdx = 0;
            for (int i = 0; i < n; i++) {
                maxProfit[i + 1] = maxProfit[i];
                int remaining = milkProduced[i];
                while (remaining > 0 && shopIdx < m) {
                    int use = Math.min(remaining, shops[shopIdx].quantity);
                    maxProfit[i + 1] += (long) use * shops[shopIdx].price;
                    remaining -= use;
                    shops[shopIdx].quantity -= use;
                    if (shops[shopIdx].quantity == 0) shopIdx++;
                }
            }
        }

        // rentals sorted descending (best rents first)
        int[] rents = new int[r];
        for (int i = 0; i < r; i++) rents[i] = Integer.parseInt(br.readLine());
        sortDesc(rents);

        // fold in rentals from the back (least productive cows)
        // maxProfit[a] becomes f(a) + g(N - a)
        long rentalSoFar = 0;
        int a = n - 1;    // index into cows from least productive side
        int rentIdx = 0;  // index into rents (best to worst)
        while (a >= 0 && rentIdx < r) {
            rentalSoFar += rents[rentIdx++];
            maxProfit[a] += rentalSoFar;
            a--;
        }

        long ans = 0;
        for (long v : maxProfit) ans = Math.max(ans, v);

        pw.println(ans);
        pw.close();
    }

    // sort int array descending
    private static void sortDesc(int[] arr) {
        Arrays.sort(arr);
        for (int i = 0, j = arr.length - 1; i < j; i++, j--) {
            int t = arr[i]; arr[i] = arr[j]; arr[j] = t;
        }
    }

    static class Shop implements Comparable<Shop> {
        int quantity, price; // wants 'quantity' gallons at 'price' cents per gallon
        Shop(int q, int p) { quantity = q; price = p; }
        public int compareTo(Shop o) { return Integer.compare(o.price, this.price); } // desc by price
    }
}