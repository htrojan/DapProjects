public class EuclidDistance implements Distance {
    @Override
    public double distance(Point p1, Point p2) {
        if (p1.getDim() != p2.getDim())
            throw new IllegalArgumentException("Can't compute distance between two points with different dimensions");

        double result = 0.0;

        //Berechne (xi - yi)^2 fuer i <= dim(p1, p2)
        for (int i = 0; i < p1.getDim(); i++) {
            result += Math.pow(p1.get(i) - p2.get(i), 2);
        }

        return Math.sqrt(result);
    }
}
