import java.util.Random;

public class PriorityQueue {
    public static void main(String[] args) {
        if (args.length != 2) {
            printUsage();
            return;
        }

        int n, k;

        try {
            n = Integer.parseInt(args[0]);
            k = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Use valid integers");
            System.out.println("Usage: PriorityQueue n k");
            return;
        }

        if (n <= 0 || k <= 0) {
            System.out.println("All numbers have to be >= 0");
        }

        Heap heap = new Heap(n + k);

        fillRandom(heap, n);

        System.out.println("Start: ");
        heap.printHeap();
        System.out.println();

        Random r = new Random();
        for (int i = 0; i < k; i++) {
            int z = r.nextInt(4); //0 - 3

            if (z >= 1) { //75%
                System.out.println("Job: " + heap.extractMax() + " ist fertig geworden");
                heap.printHeap();
            } else {
                System.out.println("Ein neuer Job wird hinzugefügt");
                heap.insert(r.nextInt(101));
                heap.printHeap();
            }
            System.out.println();
        }
    }

    /**
     * Füllt den Heap mit n Elementen zwischen 0 und 100
     */
    private static void fillRandom(Heap heap, int n) {
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            heap.insert(r.nextInt(101));
        }
    }

    static void printUsage() {
        System.out.println("Usage: PriorityQueue n k");
    }
}
