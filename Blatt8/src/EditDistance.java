import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class EditDistance {
    public static int distance(int[][] optimalField) {
        return optimalField[optimalField.length - 1][optimalField[0].length - 1];
    }

    private static int[][] calculateOptimalField(String s1, String s2) {
        int l = s1.length() + 1;
        int m = s2.length() + 1;

        int field[][] = new int[l][m];

        //Fill the zero columns/rows with initial numbers
        for (int i = 0; i < l; i++) {
            field[i][0] = i;
        }

        for (int j = 0; j < m; j++) {
            field[0][j] = j;
        }

        //Calculate field
        for (int i = 1; i < l; i++) {
            for (int j = 1; j < m; j++) {
                //-1 because of additional column/row for initial values

                int m1 = field[i - 1][j - 1]; //Replace
                int m2 = field[i - 1][j]; //Remove
                int m3 = field[i][j - 1]; //Add

                int m123 = min(m1, m2, m3) + 1;

                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    int m4 = field[i - 1][j - 1];
                    field[i][j] = min(m123, m4);
                } else {
                    field[i][j] = m123;
                }

            }
        }
        return field;
    }

    public static void main(String[] args) {
        if (args.length < 2 || args.length > 3) {
            printUsage();
            return;
        }

        boolean printOperations = false;

        if (args.length == 3)
            if (args[2].equals("-o")) {
                printOperations = true;
            } else {
                System.out.println("Third parameter must be empty or -o");
                printUsage();
                return;
            }

        String s1 = args[0];
        String s2 = args[1];

        int[][] result = calculateOptimalField(s1, s2);

        System.out.println("String1 = " + s1);
        System.out.println("String2 = " + s2);
        System.out.println("Distance = " + distance(result) + "\n");

        if (printOperations)
            printEditOperations(result, s1, s2);


    }

    private static void printUsage() {
        System.out.println("Please use: EditDistance string1 string2 [-o]");
    }

    /**
     * Prints the edit operations for an optimal field for converting s1 to s2
     *
     * @param optimalField Precalculated optimal field
     */
    private static List<OperationData> buildEditOperations(int[][] optimalField, String s1, String s2) {
        int i = s1.length();
        int j = s2.length();

        //The field where the necessary operations are stored
        LinkedList<OperationData> op = new LinkedList<>();


        while (i > 0 && j > 0) {
            //Nothing happened
            if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                op.addFirst(new OperationData(Operations.Nothing, i, j));
                --i;
                --j;
            } else {
                int m1 = optimalField[i - 1][j - 1]; //Replace
                int m2 = optimalField[i - 1][j]; //Remove
                int m3 = optimalField[i][j - 1]; //Add

                //Search for the minimum
                if (m1 <= m2 && m1 <= m3) {
                    op.addFirst(new OperationData(Operations.Replace, i, j));
                    --i;
                    --j;
                } else if (m2 <= m1 && m2 <= m3) {
                    op.addFirst(new OperationData(Operations.Remove, i, j));
                    --i;
                } else {
                    op.addFirst(new OperationData(Operations.Add, i, j));
                    --j;
                }
            }
        }

        //Now continue counting j or i down to 1 as the end has to be at (1, 1)

        while (i > 0) {
            op.addFirst(new OperationData(Operations.Remove, i--, j));
        }

        while (j > 0) {
            op.addFirst(new OperationData(Operations.Add, i, j--));
        }
        return op;
    }


    public static void printEditOperations(int[][] optimalField, String s1, String s2) {
        List<OperationData> operations = buildEditOperations(optimalField, s1, s2);

        //At least as many operations as steps required to manipulate s1
        assert (operations.size() >= optimalField[s1.length()][s2.length()]);

        StringBuilder b = new StringBuilder(s1);

        //Keeps track of the current index by counting adds and removes
        int offset = 0;

        System.out.println("Lösung für \"" + s1 + "\" --> \"" + s2 + "\" mit Gesamtkosten " + optimalField[s1.length()][s2.length()] + ":\n");
        for (OperationData op : operations) {
            int i = op.i - 1;
            int j = op.j - 1;
            int off = i + offset;
            switch (op.op) {
                case Add:
                    //Increment offset as the string has an additional char afterwards. "Position" refers to the newly added position
                    b.insert(off + 1, s2.charAt(j));
                    System.out.println("Kosten 1: Füge " + s2.charAt(j) + " an Position " + (off + 1) + " ein --> " + b.toString());
                    ++offset;
                    break;
                case Remove:
                    b.deleteCharAt(off);
                    System.out.println("Kosten 1: Lösche " + s1.charAt(i) + " an Position " + (off + 1) + " --> " + b.toString());
                    //Decrement offset after output as "Position" refers to the old position
                    --offset;
                    break;
                case Replace:
                    b.setCharAt(off, s2.charAt(j));
                    System.out.println("Kosten 1: Ersetze " + s1.charAt(i) + " durch " + s2.charAt(j) + " an Position " + (off + 1) + " --> " + b.toString());
                    break;
                case Nothing:
                    System.out.println("Kosten 0: " + s1.charAt(i) + " an Position " + (off + 1) + " --> " + b.toString());
                    break;
            }
        }
    }

    /**
     * Stores the indices and the type of an operation
     */
    private static class OperationData {
        Operations op;
        int i;
        int j;

        OperationData(Operations op, int i, int j) {
            this.op = op;
            this.i = i;
            this.j = j;
        }
    }

    private enum Operations {
        Remove, Add, Replace, Nothing
    }

    /**
     * Just searches the minimum
     * O(n)
     */
    static int min(int... values) {
        if (values.length <= 0) {
            throw new IllegalArgumentException("For calculating the minimum of numbers there have to be numbers");
        }

        int min = values[0];

        for (int i = 1; i < values.length; i++) {
            if (min > values[i])
                min = values[i];
        }

        return min;
    }


}


