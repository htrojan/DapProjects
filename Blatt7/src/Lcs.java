import com.sun.deploy.panel.ITreeNode;

import java.util.Arrays;
import java.util.Random;

public class Lcs {
    public static int[][] CalculateLcsTable(String a, String b) {
        int l = a.length() + 1;
        int m = b.length() + 1;
        //Für die Nullzeile / Spalte am Rand

        int[][] result = new int[l][m];

        for (int i = 0; i < l; i++) {
            result[i][0] = 0;
        }
        for (int i = 0; i < m; i++) {
            result[0][i] = 0;
        }

        for (int i = 1; i < l; i++) {
            for (int j = 1; j < m; j++) {
                CalculateOptimalLength(a, b, result, i, j);
            }
        }

        return result;
    }

    private static void CalculateOptimalLength(String a, String b, int[][] result, int i, int j) {
        if (a.charAt(i - 1) == b.charAt(j - 1)) { //Hier muss -1 hin, da vorher +1 für die Nullspalten genommen wurde
            result[i][j] = result[i - 1][j - 1] + 1; //Da wenn letzte beiden gleich immer zur optimalen Lsg
        } else if (result[i - 1][j] > result[i][j - 1]) {
            result[i][j] = result[i - 1][j];
        } else {
            result[i][j] = result[i][j - 1];
        }
    }

    public static String randStr(int n, Random r) {
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder res =
                new StringBuilder(n);
        while (--n >= 0) {
            res.append(alphabet.charAt(r.nextInt(alphabet.length())));
        }
        return res.toString();

    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please use one argument");
            printUsage();
            return;
        }

        int n;

        try {
            n = Integer.parseInt(args[0]);
            if (n <= 0) { //Bad code
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.println("Please use positive integers greater zero");
            return;
        }


        Random random = new Random();
        String string1 = randStr(n, random);
        String string2 = randStr(n, random);

        int[][] result = CalculateLcsTable(string1, string2);
        String lcs = calculateResult(result, string1, string2);

        System.out.println("Input:");
        System.out.println(string1);
        System.out.println(string2);
        System.out.println("Longest subsequence: " + result[n][n]);
        System.out.println("Result: " + lcs);

    }

    /**
     * @param lcsTable
     * @return Eine Optimale Lösung für das Lcs - Problem -> d.h. eine größtmögliche Subsequence
     */
    public static String calculateResult(int[][] lcsTable, String a, String b) {
        int i = a.length();
        int j = b.length();

        int lcs = lcsTable[i][j];

        char[] result = new char[lcs]; //Anzahl der Buchstaben die hineinkommen
        int c = lcs - 1; //Wegen Arraygrenzen

        //Weiter gehen bis bei einer Nullzeile angekommen.
        //Ab dort können keine weiteren Einträge mehr auftreten
        while (j > 0 && i > 0) {
            if (a.charAt(i -1) == b.charAt(j -1)) {
                //Teil der optimalen Lsg
                result[c] = a.charAt(i -1);
                c--;
                i--;
                j--;
            } else if (lcsTable[i - 1][j] > lcsTable[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        return Arrays.toString(result);
    }

    private static void printUsage() {
        System.out.println("CalculateLcsTable letterCount");
    }
}
