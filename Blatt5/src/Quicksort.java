import java.util.Arrays;
import java.util.Random;

public class Quicksort {

    public static void quicksort(int[] A, int l , int r) {
        int i = 0, j= 0;

        //Sortiert korrekt wenn für 2 oder 3 aneinanderliegende Elemente aufgerufen
        if (l < r) {
            //Nur aufrufen wenn die Grenzen korrekt sind
            i = l;
            j = r;
            int pivot = A[(l + r) / 2]; //Mittleres als Vergleichselement

            //Nur solange die Elemente links nicht größer sind als die rechten
            while (i <= j) {
                //Von links an das erste Element größer als pivot annähern
                while (A[i] < pivot) {
                    i++;
                }
                //Von rechts an das erste Element kleiner als pivot annähern
                while (A[j] > pivot) {
                    j--;
                }

                //Nur tauschen wenn das größere Element links vom kleineren steht
                //-->
                if (i <= j) {
                    int tmp = A[i];
                    A[i] = A[j];
                    A[j] = tmp;
                    i++; //Sonst gleiche Elemente nochmal
                    j--;
                }
            }

            //Wenn Teilarray zu Ende sortiert kann j< 0 der i >= A.length eintreten (An den äußeren Enden)
            assert(j < 0 || i>= A.length || A[j] <= A[i]);
            //Auf dem Intervall l bis zum gerade nach rechts getauschten Aufrufen
            //Alle Elemente auf Intervall garantiert kleiner (gleich) als Pivot
            quicksort(A, l, j);
            //Auf dem Intervall i (das Gerade nach links getauschte) bis zur rechten Grenze aufrufen
            //Alle Elemente auf Interavall garantiert größer (gleich) als Pivot
            quicksort(A, i, r);
        }



    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please use correct number of arguments");
            return;
        }

        int n;

        try {
            n = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Please enter valid number");
            return;
        }

        if (n <= 0) {
            System.out.println("Please enter number >= 0");
            return;
        }

        int[] A = new int[n];

        fillRandom(A);

        long before = System.currentTimeMillis();
        quicksort(A, 0, A.length - 1);
        long after = System.currentTimeMillis() - before;

        System.out.println("Laufzeit: " + after + "ms");
        //System.out.println(Arrays.toString(A));
        System.out.println("Sorted: " + isSorted(A));

    }

    private static void fillRandom(int[] a) {
        Random r = new Random();

        for (int i = 0; i < a.length; i++) {
            a[i] = r.nextInt();
        }
    }

    //Prueft ob Array sortiert ist
    public static boolean isSorted(int[] array) {

        //Inv: Das Array ist von 0 .. i aufsteigend sortiert
        for (int i = 1; i < array.length; i++) {

            //Wenn ein vorheriges Element groesser ist als ein nachfolgendes ist das Array nicht sortiert
            if (array[i-1] > array[i])
                return false;
        }

        return true;
    }
}
