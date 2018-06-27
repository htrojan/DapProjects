public class Heap {
    private int fillCount; //heapSize ist ein irreführender Name
    private int[] keys;

    public Heap(int size) {
        keys = new int[size];
        fillCount = 0;
    }

    public int left(int i) {
        //Plausibilität: n = 2^h -1
        //--> D.h. 2*n = n + 2^h -1 , also ein Element vor Abschluss (also das vorletzte), d.h. das Linke
        //Davon ausgehend ist jedes n weniger eine Verringerung von -2 in 2*n d.h. alle nachfolgenden sind auch
        //linke Knoten, da nur gerade Schritte. "Quadrat" (Die mögliche Induktion ist dem geneigten Leser überlassen)
        return (i + 1) * 2 - 1;
    }

    public int right(int i) {
        //Offensichtlicherweise das einen weiter rechts vom linken...
        return (i + 1) * 2;
    }

    public int parent(int i) {
        //Immer abgerundet, da Integer
        return ((i + 1) / 2) - 1;
    }

    /**
     * Stellt rekursiv von oben nach unten Heapeigenschaft wieder her
     *
     * @param i
     */
    public void heapify(int i) {

        if (i >= fillCount || i < 0)
            return; //Über oder unter max. bzw. min. Index --> Rekursionsabbruch

        int lc = left(i);
        int rc = right(i);

        int max;

        if (lc >= fillCount) { //Hat keine Kinder, Blatt --> Rekursiv bis nach oben aufrufen
            heapify(parent(i));
            return;
        } else if (rc >= fillCount) { //Hat nur linkes Kind --> Größtes Kind gefunden
            max = lc;
        } else {
            //Das Maximum der Schlüssel suchen
            assert (keys[lc] != keys[rc]); //Kein Schlüssel darf doppelt sein
            if (keys[lc] > keys[rc])
                max = lc;
            else
                max = rc;
        }

        //Das Maximum "hochtauschen" auf aktuelle Position falls größer als aktueller Wert
        if (keys[i] < keys[max]) {
            swap(i, max);
            //Rekursiv auf dem Index mit der nun alten Wurzel aufrufen
            heapify(max);
        }
        //Nur weiter machen wenn vorher getauscht werden musste
    }

    public void insert(int key) {
        if (fillCount >= keys.length) {
            throw new IllegalStateException("Can't insert key if heap is full");
        }

        keys[fillCount++] = key; //Schon hier hochzählen, da heapify() einen aktuellen Wert braucht
        //Hochtauschen
        int i = fillCount - 1;

        //Von unten nach oben durchgehen
        while (i > 0 && keys[parent(i)] < keys[i]) {
            swap(parent(i), i); //Ist größer
            i = parent(i);
        }

    }

    public int extractMax() {
        if (fillCount < 1) {
            new IllegalStateException("Heap needs entries to extract maximum");
        }
        //Maximum steht immer oben --> Zwischenspeichern
        int max = keys[0];

        //Wurzel durch letztes Element ersetzen
        keys[0] = keys[--fillCount]; //fillCount wird gleichzeitig erniedrigt, da das letzte Element entfernt wird
        heapify(0);
        return max;
    }

    public void printHeap() {
        int maxColumn = 2;

        for (int i = 1; i <= fillCount; i++) {
            if (i == maxColumn) {
                System.out.println(); //Leerzeile
                maxColumn *= 2;
            }

            System.out.print(keys[i - 1] + "  ");
        }

        System.out.println();
    }

    /**
     * Swaps two elements at positions i1 and i2
     */
    private void swap(int i1, int i2) {
        int tmp = keys[i1];
        keys[i1] = keys[i2];
        keys[i2] = tmp;
    }
}
