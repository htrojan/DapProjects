
import java.util.Random;

public class BackPack {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Illegal cout of arguments");
            printUsage();
            return;

        }

        //Hässliche Eingabe
        int n;
        try {
            n = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("First parameter was no number");
            printUsage();
            return;
        }

        int w;

        try {
            w = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Second parameter was no number");
            printUsage();
            return;
        }

        int p;
        try {
            p = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.out.println("Third parameter was no number");
            printUsage();
            return;
        }

        if (n <= 0 || w <= 0 || p <= 0) {
            System.out.println("All parameters have to be >= 0");
            return;
        }

        Article[] articles = generateArticles(n, p);

        int maxValue = maxValue(articles, w);

        System.out.println("Maximale Value: " + maxValue);
    }

    /**
     * Generates n articles
     */
    private static Article[] generateArticles(int n, int p) {
        Random r = new Random();
        Article[] articles = new Article[n];

        for (int i = 0; i < n; i++) {
            int weight = (int) (0.8 * p + r.nextInt((int) ((1.25f - 0.8f) * p)));
            int value = 100 + r.nextInt(1000 - 100);
//            System.out.println("Weight = " + weight);
//            System.out.println("Value = " + value);

            articles[i] = new Article(value, weight);
        }

        return articles;
    }

    public static int maxValue(Article[] art, int weightLimit) {
        //i sind Objekte
        //j ist Gewicht

        int[][] field = new int[art.length + 1][weightLimit + 1];

        //Initialisiere mit Nullzeile / Spalte

        //Bei Gewicht von 0 kann kein Objekt mitgenommen werden
        for (int i = 0; i < art.length + 1; i++) {
            field[i][0] = 0;
        }

        //Bei 0 Objekten kann kein Wert mitgenommen werden
        for (int i = 0; i < weightLimit + 1; i++) {
            field[0][i] = 0;
        }

        for (int i = 1; i < art.length + 1; i++) {
            for (int j = 1; j < weightLimit + 1; j++) {
                int n1 = field[i - 1][j]; //Nicht Teil der Lösung

                //art[i-1] da Bonuszeile mit 0 initialisiert
                if (art[i-1].weight <= j) { //Der Artikel passt in den Rucksack, j ist das aktuell betrachtete Maximalgewicht
                    int n2 = field[i - 1][j - art[i-1].weight] + art[i-1].value; //Teil der Lösung
                    field[i][j] = max(n1, n2);
                } else {
                    field[i][j] = n1;
                }

            }
        }

        return field[art.length][weightLimit];
    }

    static int max(int i1, int i2) {
        if (i1 > i2)
            return i1;
        else
            return i2;
    }

    private static void printUsage() {
        System.out.println("BackPack Waren Gewichtsschranke p");
    }
}
