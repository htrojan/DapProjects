import java.util.Arrays;
import java.util.Random;

public class Sortierung {
	
	//Mit dieser Methode wird der Merge-Sort erstmals aufgerufen
	public static void mergeSort(int[] array) {
		int[] tmpArray = new int[array.length];
		mergeSort(array, tmpArray, 0, array.length -1); //Gehe in die rekursive Merge-Sort Methode
	}

	//Rekursive Merge-Sort Methode
	public static void mergeSort(int[] array, int[] tmpArray, int left, int right) {
		
		//Rekursionverankerung: Ein Array mit einem Element ist sortiert
		if(left < right) {
			//Dies ist die Mitte
			int m = (left+right)/2;
			
			//Teile Problem
			mergeSort(array, tmpArray, left, m);
			mergeSort(array, tmpArray, m + 1, right);
			
			//Füge Problem wieder zusammen
			merge(array, tmpArray, left, m, right);
			
			//Sortiertes array [left...right] steht in selber Stelle in tmpArray, kopiere Ergebnis in richtiges Array
			for(int i = left; i <= right; i++) { //Alle Einträge von left bis i sind kopiert
				array[i] = tmpArray[i];
			}
		}
	}
	
	//Diese Funktion fügt zwei sortierte Sub-Array zusammen (nach tmpArray) 
	public static void merge(int[] array, int[] tmpArray, int left, int middle, int right) {
		//Laufvariablen, die auf die sortierten Sub-Arrays zeigen
		int l = left, r = middle+1;
		
		//Schleife, die den Array in tmpArray einsortiert. Invariante: Alle Einträge von left bis i sind in tmpArray sortiert
		for(int i = left; i <= right; i++) {
			
			assert l < middle : "Der linke Laufzähler muss kleiner sein, als die Mitte zwischen den beiden Arrays";
			assert r < right : "Der rechte Laufzähler muss kleiner sein, als das Ende des rechten Arrays";
			assert i < right : "Der kopier Laufzähler muss kleiner sein, als das Ende des Arrays";
			
			//Element aus rechtem Array einfügen
			if(array[l] > array[r]) {
				tmpArray[i] = array[r];
				r++;
				
				//Falls das rechte Array am Ende ist, füge ganzes linkes Array ein
				if(r > right)						
				while(l <= middle)
					tmpArray[++i] = array[l++];
				
			//Element aus linkem Array einfügen
			}else {
				tmpArray[i] = array[l];
				l++;
				
				//Falls das linke Array leer am Ende ist, füge ganzes rechtes Array ein
				if(l > middle)
				while(r <= right)
					tmpArray[++i] = array[r++];
			}
		}
	}
	
    public static void insertionSort(int[] array) {
        if(array.length < 1) //Annahme: Das Array ist auf den Indizes 0 .. 0 sortiert, da nur ein Eintrag
            return;
        

        //Inv: Das Array ist auf dem Indizes 0 .. j-1 sortiert
        //-> Muss bis j = maxIndex ( array) = array.length - 1 durchgegangen werden
        for (int j = 1; j < array.length; j++) {
            int key = array[j]; //Zahl die in das Array einsortiert werden soll
            
            int i = j -1; //Index des Groesten schon sortierten Element
            
            //Findet die Stelle, an die key einsortiert werden soll. Inv: alle Elemente von i bis j-1 sind groesser als key.
            while (i >= 0 && array[i] > key) {
               array[i+1] = array[i]; 	//Verschiebe Arrayeinträge nach rechts (bis kleinerer Eintrag gefunden). (Kurzzeitiges Doppelauftauchen)
               							//Dadurch koennte key aus Array gelöscht werden, deswegen brauchen wir ihn in der Variable
               i--;
            }

            assert (i+1) >= 0 : "Das Element muss innerhalb der Arraygrenzen einsortiert werden";
            
            //Key an die richtige Stelle einsortieren, nun ist er bis j sortiert
            array[i+1] = key;
            
            
            //Wenn i = -1 soll das Element auf den Index null gesetzt werden. Falls das Array bis zu j = 2 oder mehr sortiert wurde
            //muss deshalb gesondert die richtige Einsortierung geprÃ¼ft werden
            assert  ((i+1) == 0 && j >= 1 && array[0] < array[1])|| i+1 > 0 && array[i] <= key : "Das aktuelle Element wurde nicht richtig einsortiert";
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

    public static void main(String[] args) {
        int size;
        
        //Mindestens ein Kommandozeilenparameter wird benötigt
        if (args.length < 1) {
            printUsage();
            return;
        }
        
        //Ist erstes Argument ein Int?
        try {
            size = Integer.parseInt(args[0]);
            
        //Fehlermeldung
        } catch (NumberFormatException e) {
            System.out.println("Das Uebergebene Argument \"size\" war keine Zahl");
            printUsage();
            return;
        }
        
        
        //Ist die uebergebene Zahl zulässig?
        if (size <= 0) {
            System.out.println("size muss > 0 sein");
            return;
        }

        int[] array = new int[size];

        //Hier sollen die Kommandozeilenparameter spaeter drin stehen
        String fillMethod, sortMethod;

        //Wenn keine Angabe mit Zufallszahlen befuellen und Merge Sort benutzen
        if (args.length < 2) {
            sortMethod = "merge";
            fillMethod = "rand";
            
        //Wenn es nur einen Parameter gab, diesen nutzen und random fuellen
        }else if(args.length < 3){
            sortMethod = args[1]; 
            fillMethod = "rand";
            
        // Beide Parameter nutzen 
        }else{
        	sortMethod = args[1];
        	fillMethod = args[2];
        }
            
        //Zweiten Parameter parsen
        switch (fillMethod) {
            case "rand" :
                //Inv: array[0 .. i] enthaelt neue pseudo-zufaellige Zahlen
            	Random generator = new Random(); 
                for (int i = 0; i < array.length; i++) {
                    array[i] = generator.nextInt();
                }
                break;
                
            case "auf" :
                //Inv: array[0 .. i] enthaelt aufsteigende Zahlen von 0 bis i
                for (int i = 0; i < array.length; i++) {
                    array[i] = i; //Der momentane Laufindex ist groesser als der letzte
                }
                break;
                
            case "ab" :
                //Inv: array[0 .. i] enthaelt absteigend Zahlen von array.length bis (array.length - i)
                for (int i = 0; i < array.length; i++) {
                    array[i] = array.length - i;
                }
                break;
            default:
                //Ungueltiger Parameter
                printUsage();
                return;
        }

        long start, end, msec;

        //Zeitmessung
        start = System.currentTimeMillis();
        
        //Sortiermethode Festlegen
        switch(sortMethod) {
        
        case "insert":  insertionSort(array);
        				break;
        				
        case "merge":	mergeSort(array);
        				break;
        				
        default:
        				//Ungueltiger Parameter
        				printUsage();
        				return;
        }
        
        end = System.currentTimeMillis();

        msec = end - start;
        
        //Sortierversuch geglueckt?
        if (isSorted(array))
            System.out.println("Feld sortiert!");
        else
            System.out.println("Feld NICHT sortiert");
        
        System.out.println("Benoetigte Zeit: " + msec + "ms");
        
        //Ausgabe
        if (size <= 100) {
            System.out.println("Feld:");
            System.out.println(Arrays.toString(array));
        }

    }

    //oft wiederverwendete Funktion
    public static void printUsage(){
        System.out.println("Usage: Sortierung size [insert|merge [rand|auf|ab]}]");
    }
}
