import java.io.*;
import java.util.*;

public class tttt {
    static char[][] B = new char[3][3];

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("tttt.in"));
        for (int i = 0; i < 3; i++) {
            String line = br.readLine().trim();
            for (int j = 0; j < 3; j++) B[i][j] = line.charAt(j);
        }
        br.close();

        int singleWins = 0;
        int teamWins = 0;

        for (char ch = 'A'; ch <= 'Z'; ch++) {
            if (cowWins(ch)) singleWins++;
        }

        for (char c1 = 'A'; c1 <= 'Z'; c1++) {
            for (char c2 = (char)(c1 + 1); c2 <= 'Z'; c2++) {
                if (teamWins(c1, c2)) teamWins++;
            }
        }

        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("tttt.out")));
        pw.println(singleWins);
        pw.println(teamWins);
        pw.close();
    }

    static boolean cowWins(char ch) {
        // diagonals
        if (B[0][0] == ch && B[1][1] == ch && B[2][2] == ch) return true;
        if (B[0][2] == ch && B[1][1] == ch && B[2][0] == ch) return true;
        // rows and columns
        for (int i = 0; i < 3; i++) {
            if (B[i][0] == ch && B[i][1] == ch && B[i][2] == ch) return true;
            if (B[0][i] == ch && B[1][i] == ch && B[2][i] == ch) return true;
        }
        return false;
    }

    static boolean check3(char a, char b, char c, char c1, char c2) {
        // all must be c1 or c2
        if (!(a == c1 || a == c2)) return false;
        if (!(b == c1 || b == c2)) return false;
        if (!(c == c1 || c == c2)) return false;
        // both characters must appear at least once
        if (a != c1 && b != c1 && c != c1) return false;
        if (a != c2 && b != c2 && c != c2) return false;
        return true;
    }

    static boolean teamWins(char c1, char c2) {
        // diagonals
        if (check3(B[0][0], B[1][1], B[2][2], c1, c2)) return true;
        if (check3(B[0][2], B[1][1], B[2][0], c1, c2)) return true;
        // rows and columns
        for (int i = 0; i < 3; i++) {
            if (check3(B[i][0], B[i][1], B[i][2], c1, c2)) return true;
            if (check3(B[0][i], B[1][i], B[2][i], c1, c2)) return true;
        }
        return false;
    }
}