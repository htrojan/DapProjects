import com.sun.glass.ui.Size;

import javax.tools.JavaCompiler;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

@SuppressWarnings("ALL")
public class Sortierung {

    public static void insertionSort(int[] array) {
        if(array.length < 1)
            return;

        assert array.length >= 1 : "Das Array muss Einträge haben um sortiert zu werden";
        //Annahme: Das Array ist auf den Indizes 0 .. 0 sortiert, da nur ein Eintrag

        //Inv: Das Array ist auf dem Indices 1 .. j sortiert
        //-> Muss bis j = maxIndex ( array) = array.length - 1 durchgegangen werden
        for (int j = 1; j < array.length; j++) {
            int key = array[j]; //Aktueller Wert der Zahl am index j, die in das durch Inv. schon sortierte Array
                                //eingeordnet werden soll
            int i = j -1; //Größter Index des durch Inv. schon sortierten Arrays

            //Inv: Das Array ist sortiert von 1 .. j. Ggf. ist die nächstgrößere Zahl an Key 2-mal hintereinander
            //vorhanden; Der Wert Key kommt dann nicht im Array vor.
            //i+1 zeigt auf den Index, wo Key einsortiert werden muss (ggf. wenn existent damit auch auf den doppelten Eintrag)
            while (i >= 0 && array[i] > key) {
               array[i+1] = array[i]; //Falls i größer als Key den Eintrag von i nach rechts verschieben
               i--;//Für den nächst kleineren Eintrag wiederholen

            }

            assert(i + 1) >= 0 : "Das Element muss innerhalb der Arraygrenzen einsortiert werden";
            //Key an die richtige Stelle einsortieren
            array[i+1] = key;
            //Wenn i = -1 soll das Element auf den Index null gesetzt werden. Falls das Array bis zu j = 2 oder mehr sortiert wurde
            //muss deshalb gesondert die richtige Einsortierung geprüft werden
            assert  ((i+1) == 0 && j >= 1 && array[0] < array[1])|| i+1 > 0 && array[i] <= key : "Das aktuelle Element wurde nicht richtig einsortiert";
        }
    }

    public static boolean isSorted(int[] array) {
        //Inv: Das Array ist von 0 .. i aufsteigend sortiert
        for (int i = 1; i < array.length; i++) {
            //Wenn ein vorheriges Element größer ist als ein nachfolgendes ist das Array nicht sortiert
            if (array[i-1] > array[i])
                return false;
        }

        return true;
    }

    public static void main(String[] args) {
        int size;

        if (args.length < 1) {
            printUsage();
            return;
        }

        try {
            size = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Das übergebene Argument \"size\" war keine Zahl");
            printUsage();
            return;
        }

        if (size < 0) {
            System.out.println("size muss > 0 sein");
            return;
        }

        int[] array = new int[size];
        Random generator = new Random();

        String sortMethod;

        //Wenn keine Angabe mit Zufallszahlen befüllen
        if (args.length < 2)
            sortMethod = "rand";
        else
            sortMethod = args[1]; //Ansonsten angegebenen Parameter nutzen

        //Zweiten Parameter parsen
        switch (sortMethod) {
            case "rand" :
                //Inv: array[0 .. i] enthält zufällige Zahlen
                for (int i = 0; i < array.length; i++) {
                    array[i] = generator.nextInt();
                }
                break;
            case "auf" :
                //Inv: array[0 .. i] enthält aufsteigende Zahlen von 0 bis i
                for (int i = 0; i < array.length; i++) {
                    array[i] = i; //Der momentane Laufindex ist größer als der letzte
                }
                break;
            case "ab" :
                //Inv: array[0 .. i] enthält absteigend Zahlen von array.length bis (array.length - i)
                for (int i = 0; i < array.length; i++) {
                    array[i] = array.length - i;
                }
                break;
            default:
                //Ungültiger Parameter
                printUsage();
                return;
        }

        long start, end, msec;

        //Zeitmessung
        start = System.currentTimeMillis();
        insertionSort(array);
        end = System.currentTimeMillis();

        msec = end - start;

        if (isSorted(array))
            System.out.println("Feld sortiert!");
        else
            System.out.println("Feld NICHT sortiert");

        System.out.println("Benötigte Zeit: " + msec + "ms");
        if (size <= 100) {
            System.out.println("Feld:");
            System.out.println(Arrays.toString(array));
        }

    }

    public static void printUsage(){
        System.out.println("Uage: Sortierung size [rand|auf|ab]");
    }
}
