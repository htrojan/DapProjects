public abstract class Simplex {
    int dimension;
    Point[] points;

    /**
     * Ein beliebiges Polygon mit dimension + 1 Eckpunkten
     *
     * @param dimension Die Dimension des Simplex
     * @param points    Die Eckpunkte. Genau dimension + 1 stück
     */
    Simplex(int dimension, Point... points) {
        if (points.length != dimension + 1)
            throw new IllegalArgumentException("Trying to create Simplex of dimension " + dimension + " with the wrong number of points");
        this.dimension = dimension;
        this.points = points;
    }

    public int getDim() {
        return dimension;
    }

    public Point getPoint(int index) {
        if (index < 0 || index >= dimension)
            throw new IllegalArgumentException("Trying to access index " + index + " of Simplex with " + (dimension + 1) + " points");
        return points[index];
    }

    /**
     * Gibt den Umfang des Simplex zurueck
     */
    public double perimeter() {
        Distance d = new EuclidDistance();
        double perimeter = 0;

        //Laueft durch alle Kanten, aber nicht doppelt!
        for (int i = 0; i <= dimension; i++)
            for (int j = i; j <= dimension; j++) {
                perimeter += d.distance(points[i], points[j]);
            }

        return perimeter;
    }

    /**
     * Prueft, ob die angegebenen Punkte ein valider Simplex sind
     */
    public abstract boolean validate();
}
