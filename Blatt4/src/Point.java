@SuppressWarnings("ALL")
public class Point {
    private int dimension;
    private double[] values;

    public Point(int dimension, double... values) {
        this.dimension = dimension;
        if (values.length != dimension)
            throw new IllegalArgumentException("Trying to initiate Point of dimension " + dimension + " with the wrong count of values");
        this.values = values;
    }

    public int getDim() {
        return dimension;
    }

    @Override
    public String toString() {
        String string = "[";

        //Bis auf die letzte Koordinate durchlaufen
        for (int i = 0; i < dimension - 1; i++) {
            string += values[i];
            string += "; ";
        }
        //Nach der letzten Koordinate kommt kein Komma mehr
        //dimension - 1 = letzter Index
        string += values[dimension - 1];

        string += "]";
        return string;
    }

    public double get(int i) {
        if (i < 0 || i >= dimension)
            throw new IllegalArgumentException("Trying to access dimension " + i + " of point with " + dimension + " dimensions");
        return values[i];
    }


}

