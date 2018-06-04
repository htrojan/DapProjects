public class EditDistance {
    public static int distance(String s1, String s2) {
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
                int m2 = field[i - 1][j]; //Delete
                int m3 = field[i][j - 1]; //Add

                int mg = min(m1, m2, m3) +1;

                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    int m4 = field[i - 1][j - 1];
                    field[i][j] = min(mg, m4);
                } else {
                    field[i][j] = mg;
                }

            }
        }
        return field[l - 1][m - 1];
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Please use: EditDistance string1 string2");
            return;
        }

        String s1 = args[0];
        String s2 = args[1];

        System.out.println("String1 = " + s1);
        System.out.println("String2 = " + s2);
        System.out.println("Distance = " + distance(args[0], args[1]));
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


