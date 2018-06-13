import java.sql.SQLOutput;

public class TraverseTree {
    public static void main(String[] args) {
        int[] input = {13, 17, 5, 3, -10, 100, 40, -5, 4, 12, 11};

        SearchTree tree = new SearchTree(input);

        System.out.println("PreOrder: ");
        tree.walkPreOrder( (Integer i) -> System.out.println(i + ", "));

        System.out.println("InOrder: ");
        tree.walkInOrder((Integer i) -> System.out.println(i + ", "));

        System.out.println("PostOrder: ");
        tree.walkPostOrder((Integer i) -> System.out.println(i + ", "));
    }
}
