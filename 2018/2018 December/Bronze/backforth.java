import java.io.*;
import java.util.*;

public class backforth {
    static boolean[] possible = new boolean[2001]; // milk values range roughly 0..2000

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("backforth.in"));
        StringTokenizer st;
        ArrayList<Integer> B1 = new ArrayList<>();
        ArrayList<Integer> B2 = new ArrayList<>();

        // read 10 buckets for barn1
        st = new StringTokenizer(br.readLine());
        while (st.hasMoreTokens()) B1.add(Integer.parseInt(st.nextToken()));
        // read 10 buckets for barn2 (line may be on same or next line)
        String secondLine = br.readLine();
        if (secondLine == null) {
            // if first line had all 20 numbers, secondLine will be null
            // but problem input gives two lines, so usually not needed
            throw new IOException("Unexpected input format");
        }
        st = new StringTokenizer(secondLine);
        while (st.hasMoreTokens()) B2.add(Integer.parseInt(st.nextToken()));
        br.close();

        // start with 1000 gallons in barn1
        tuesday(1000, B1, B2);

        int count = 0;
        for (boolean b : possible) if (b) count++;

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("backforth.out")));
        pw.println(count);
        pw.close();
    }

    // Tuesday: pick one bucket from B1, move it to B2, subtract from b1milk, then go to wednesday
    static void tuesday(int b1milk, ArrayList<Integer> B1, ArrayList<Integer> B2) {
        for (int i = 0; i < B1.size(); i++) {
            int x = B1.get(i);
            ArrayList<Integer> newB1 = new ArrayList<>(B1);
            ArrayList<Integer> newB2 = new ArrayList<>(B2);
            newB1.remove(i);
            newB2.add(x);
            wednesday(b1milk - x, newB1, newB2);
        }
    }

    // Wednesday: pick one bucket from B2, move to B1, add to b1milk, then thursday
    static void wednesday(int b1milk, ArrayList<Integer> B1, ArrayList<Integer> B2) {
        for (int i = 0; i < B2.size(); i++) {
            int x = B2.get(i);
            ArrayList<Integer> newB1 = new ArrayList<>(B1);
            ArrayList<Integer> newB2 = new ArrayList<>(B2);
            newB2.remove(i);
            newB1.add(x);
            thursday(b1milk + x, newB1, newB2);
        }
    }

    // Thursday: pick one bucket from B1, move to B2, subtract, then friday
    static void thursday(int b1milk, ArrayList<Integer> B1, ArrayList<Integer> B2) {
        for (int i = 0; i < B1.size(); i++) {
            int x = B1.get(i);
            ArrayList<Integer> newB1 = new ArrayList<>(B1);
            ArrayList<Integer> newB2 = new ArrayList<>(B2);
            newB1.remove(i);
            newB2.add(x);
            friday(b1milk - x, newB1, newB2);
        }
    }

    // Friday: pick one bucket from B2, move to B1, record resulting b1milk
    static void friday(int b1milk, ArrayList<Integer> B1, ArrayList<Integer> B2) {
        for (int i = 0; i < B2.size(); i++) {
            int x = B2.get(i);
            int finalMilk = b1milk + x;
            if (finalMilk >= 0 && finalMilk < possible.length) possible[finalMilk] = true;
        }
    }
}