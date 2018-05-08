public class Point {
    private int dimension;
    private double[] values;

    public Point(int dimension, double... values) {
        this.dimension = dimension;
        if (values.length != dimension)
            throw new IllegalArgumentException("Trying to initiate Point of dimension " + dimension + " with the wrong count of values");
        this.values = values;
    }

    /**
     * Dient nur zum Klasseninternen Aufruf
     */
    private Point(int dimension) {
        this.dimension = dimension;
        values = new double[dimension];
        for(double d : values) 
        	d = 0;
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


    /**
     * Berechnet das Skalarprodukt mit dem gegebenen Punkt
     */
    public double scalar(Point p) {
        if (p.dimension != this.dimension)
            throw new IllegalArgumentException("Can not calculate scalar between points with different dimensions");

        double result = 0.0;
        for (int i = 0; i < dimension; i++) {
            result += this.values[i] * p.values[i];
        }

        return result;
    }

    /**
     * Zieht den Punkt p von diesem ab und gibt das Ergebnis als neuen Vektor zurück.
     *
     * @param p Der Punkt, der abgezogen wird. Muss die gleiche Dimension wie der aufgerufene Punkt besitzen.
     * @return Der neue Punkt mit der gleichen Dimension wie p und der Aufrufer
     */
    public Point minus(Point p) {
        if (p.dimension != this.dimension)
            throw new IllegalArgumentException("Can not subtract vectors between points with different dimensions");

        Point result = new Point(dimension);

        for (int i = 0; i < dimension; i++) {
            result.values[i] = this.values[i] - p.values[i];
        }

        return result;
    }
}

