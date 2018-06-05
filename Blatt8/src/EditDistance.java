import java.util.Arrays;

@SuppressWarnings("ALL")
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

                int mg = min(m1, m2, m3) + 1;

                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    int m4 = field[i - 1][j - 1];
                    field[i][j] = min(mg, m4);
                } else {
                    field[i][j] = mg;
                }

            }
        }
        return field;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Please use: EditDistance string1 string2");
            return;
        }

        String s1 = args[0];
        String s2 = args[1];

        int[][] result = calculateOptimalField(s1, s2);

        System.out.println("String1 = " + s1);
        System.out.println("String2 = " + s2);
        System.out.println("Distance = " + distance(result));

        printEditOperations(result, s1, s2);
    }

    /**
     * Prints the edit operations for an optimal field for converting s1 to s2
     *
     * @param optimalField Precalculated optimal field
     */
    private static OperationData[] buildEditOperations(int[][] optimalField, String s1, String s2) {
        int i = s1.length() ;
        int j = s2.length() ;

        int length = optimalField[i][j];

        //Max operations are given through the max length of the strings
        int operationLength = max(s1.length(), s2.length());
        //The field where the necessary operations are stored
        OperationData[] op = new OperationData[operationLength];
        //Index variable for op
        int k = operationLength - 1;


        while (i > 0 || j > 0) {
            //Nothing happened
            if (s1.charAt(i -1) == s2.charAt(j -1)) {
                op[k--] = new OperationData(Operations.Nothing, i, j);
                --i;
                --j;
            } else {
                int m1 = optimalField[i - 1][j - 1]; //Replace
                int m2 = optimalField[i - 1][j]; //Remove
                int m3 = optimalField[i][j - 1]; //Add

                if (m1 <= m2 && m1 <= m3) {
                    op[k--] = new OperationData(Operations.Replace, i, j);
                    --i;
                    --j;
                } else if (m2 <= m1 && m2 <= m3) {
                    op[k--] = new OperationData(Operations.Remove, i, j);
                    --i;
                } else {
                    op[k--] = new OperationData(Operations.Add, i, j);
                    --j;
                }
            }
        }

        //Now continue counting j or i down to 1

        while (i > 0) {
            op[k--] = new OperationData(Operations.Remove, i--, j);
        }

        while (j > 0) {
            op[k--] = new OperationData(Operations.Add, i, j--);
        }
        return op;
    }

    private static int max(int i1, int i2) {
        if (i1 > i2)
            return i1;
        else
            return i2;
    }

    public static void printEditOperations(int[][] optimalField, String s1, String s2) {
        OperationData[] operations = buildEditOperations(optimalField, s1, s2);
        char[] s = Arrays.copyOf(s1.toCharArray(), operations.length);

        //Keeps track of the current index by counting adds and removes
        int offset = 0;

        for (OperationData op : operations) {
            int i =  op.i -1;
            int j = op.j -1;
            switch (op.op) {
                case Add:
                    ++offset;
                    addAtIndex(s, ++i, s2.charAt(op.j));
                    System.out.println("Kosten 1: Füge " + s2.charAt(j) + " an Position " + (op.i + offset) + " ein --> " + new String(s));
                    break;
                case Remove:
                    --offset;
                    removeAtIndex(s, i);
                    System.out.println("Kosten 1: Lösche " + s1.charAt(i) + " an Position " + (op.i + offset) + " ein --> " + new String(s));
                    break;
                case Replace:
                    s[i] = s2.charAt(j);
                    System.out.println("Kosten 1: Ersetze " + s1.charAt(i) + " durch " + s2.charAt(j) + " an Position " + (op.i + offset) + " --> " + new String(s));
                    break;
                case Nothing:
                    System.out.println("Kosten 0: " + s1.charAt(i) + " an Position " + (op.i + offset) + " --> " + new String(s));
                    break;
            }
        }
    }

    private static void removeAtIndex(char[] c, int index) {
        //copies all following indices one to the left to overwrite the deleted element
        System.arraycopy(c, index + 1, c, index, c.length - 1 - index);
        c[c.length-1] = '\0';
    }

    private static void addAtIndex(char[] c, int index, char ch) {
        //copies all following indices one to the right to make place for the new character
        System.arraycopy(c, index, c, index + 1, c.length - index -1);
        c[index] = ch;
    }

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


