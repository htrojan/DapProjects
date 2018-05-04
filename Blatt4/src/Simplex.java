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
            throw new IllegalArgumentException("Trying to initiate Simplex of dimension " + dimension + " with the wrong number of points");
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
     * Gibt den Umfang des Simplex zurück
     */
    public double perimeter() {
        Distance d = new EuclidDistance();
        double perimeter = 0;

        //Da dimension + 1 Punkte vorhanden ist dimension der letzte Index von points[]
        //--> Erfüllt da Konstruktor prüft
        for (int i = 0; i < dimension; i++) {
            perimeter += d.distance(points[i], points[i + 1]);
        }

        return perimeter;
    }

    /**
     * Prüft, ob die angegebenen Punkte ein valider Simplex sind
     */
    public abstract boolean validate();
}
