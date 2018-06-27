public class TestHeap {

    public static void main(String[] args) {
        int[] add = new int[]{3, 9, 12, 15, 14, 13, 5};

        Heap heap = new Heap(50);

        for (int i : add) {
            heap.insert(i);
            System.out.println("\n");
            heap.printHeap();
        }

        System.out.println("\nExtract max");
        heap.extractMax();
        heap.printHeap();
    }
}
