public class Lcs {
    public static int Lcs(String a, String b) {
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

        return result[l-1][m-1];
    }

    private static void CalculateOptimalLength(String a, String b, int[][] result, int i, int j) {
        if (a.charAt(i - 1) == b.charAt(j - 1)) { //Hier muss -1 hin, da vorher +1 für die Nullspalten genommen wurde
            result[i][j] = result[i-1][j-1] + 1; //Da wenn letzte beiden gleich immer zur optimalen Lsg
        } else if (result[i - 1][j] > result[i][j - 1]) {
            result[i][j] = result[i - 1][j];
        } else {
            result[i][j] = result[i][j-1];
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Please use one argument");
            printUsage();
            return;
        }

        int result = Lcs(args[0], args[1]);

        System.out.println("Longest subsequence: " + result);

    }

    private static void printUsage() {
        System.out.println("Lcs string1 string2");
    }
}
