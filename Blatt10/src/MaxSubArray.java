import java.util.Arrays;
import java.util.Random;

public class MaxSubArray {
    public static int naiveMaxSubArray(int[] a) {

        //Das Array von vorne nach hinten durchgehen.
        //Bei jedem Durchgang von dem aktuellen Index aus das höchste Subarray suchen,
        //indem die Zahlen aufsummiert werden und das höchste Zwischenergebnis jeweils
        //gespeichert wird.
        int maxSum = 0;

        for (int i = 0; i < a.length; i++) {
            int currentSum = 0;

            //Ab Index i alle Zahlen aufsummieren und
            //jeweils die höchste Summe speichern
            for (int j = i; j < a.length; j++) { //Laufzeit Gaußsche Summe n*(n+1)/2 = (n^2 + n)/2 = = O(n^2)
                //Aber effektiv ca. um 1/3 schneller als n^2
                currentSum += a[j];
                if (currentSum > maxSum)
                    maxSum = currentSum;
            }
        }

        return maxSum;
    }

    /**
     * Laufzeit O(n), da das Array nur einmal durchlaufen wird
     */
    public static int dynamicMaxSubArray(int[] a) {
        int[] maxSub = new int[a.length + 1];

        maxSub[0] = 0; //Initialisierung. Leere Menge als Lösungsmöglichkeit.
        //Erster Schritt benötigt dadurch keine Sonderbehandlung

        //Die Summe der Elemente des Kanidatenarrays
        int canidateSum = 0;

        for (int j = 1; j < a.length + 1; j++) {
            //bei a[j-1] muss wegen dem Zusatzeintrag maxSub[0] der Index angepasst werden
            //a[j-1] kann auch eine Minuszahl sein: Lsg von 3 -1 4 enthält -1

            if (canidateSum + a[j - 1] >= 0)
                canidateSum += a[j - 1];
            else  // canidateSum + a[j-1] < 0
                canidateSum = 0;

            maxSub[j] = Math.max(maxSub[j - 1], canidateSum); //Maximum der aktuellen und letzten Lösung
        }

        return maxSub[a.length];
    }

    public static void main(String[] args) {
        int[] a = randomArray(1000000);
        System.out.println("--Maximale Summe--");
        //Zeitmessung
        long before = System.currentTimeMillis();
        int naive = naiveMaxSubArray(a);
        long naiveTime = System.currentTimeMillis() - before;

        before = System.currentTimeMillis();
        int dynamic = dynamicMaxSubArray(a);
        long dynamicTime = System.currentTimeMillis() - before;

//        System.out.println("Input: " + Arrays.toString(a));
        System.out.println("Naiv: " + naive);
        System.out.println("Zeit: " + naiveTime + " ms");
        System.out.println("Dynamisch: " + dynamic);
        System.out.println("Zeit: " + dynamicTime + " ms");
    }

    private static int[] randomArray(int n) {
        Random r = new Random();
        int a[] = new int[n];
        for (int i = 0; i < a.length; i++) {
            a[i] = r.nextInt(100) - 50;
        }

        return a;
    }
}
