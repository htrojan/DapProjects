import java.util.function.Consumer;

public class SearchTree {
    private Node root;

    SearchTree(int... numbers) {
        addAll(numbers);
    }

    public void addAll(int... numbers) {
        for (int number : numbers) {
            add(number);
        }
    }

    /**
     * Fügt ein Element in den root-Node des Baumes ein.
     * Dient nur als Wrapper für add(int number, Node node)
     */
    public void add(int number) {

        if (root == null)
            root = new Node(number);
        else
            add(number, root);
    }

    /**
     * Fügt ein Element rekursiv in den Baum ein
     *
     * @param number Das einzufügende Element
     * @param node   Der Knoten ab dem das Element eingefügt werden soll
     */
    private void add(int number, Node node) {
        assert (node != null); //Kann nicht vorkommen
        assert (node.key != number); //Darf nicht vorkommen

        if (number < node.key) {
            if (node.left == null) {
                node.left = new Node(number);
            } else {
                add(number, node.left);
            }
        } else { //number > node.key
            if (node.right == null) {
                node.right = new Node(number);
            } else {
                add(number, node.right);
            }
        }
    }

    public void walkPreOrder(Consumer<Integer> action) {
        walkPreOrder(action, root);
    }

    public void walkPostOrder(Consumer<Integer> action) {
        walkPostOrder(action, root);
    }

    public void walkInOrder(Consumer<Integer> action) {
        walkInOrder(action, root);
    }

    private void walkPreOrder(Consumer<Integer> action, Node node) {
        if (node == null)
            return;

        action.accept(node.key);
        walkPreOrder(action, node.left);
        walkPreOrder(action, node.right);
    }

    private void walkPostOrder(Consumer<Integer> action, Node node) {
        if (node == null)
            return;

        walkPostOrder(action, node.left);
        walkPostOrder(action, node.right);
        action.accept(node.key);
    }

    private void walkInOrder(Consumer<Integer> action, Node node) {
        if (node == null)
            return;

        walkInOrder(action, node.left);
        action.accept(node.key);
        walkInOrder(action, node.right);
    }

    private class Node {
        Node left, right;
        int key;

        public Node(Node left, Node right, int key) {
            this.left = left;
            this.right = right;
            this.key = key;
        }

        public Node(int key) {
            this.key = key;
        }
    }
}
