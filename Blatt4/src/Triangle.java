public class Triangle extends Simplex {


    /**
     * Ein Dreieck bestehend aus 3 Eckpunkten
     *
     * @param points Die 3 Eckpunkte
     */
    Triangle(Point... points) {
        super(2, points);
    }

    @Override
    public boolean validate() {
        for (Point point : points) {
            if (point.getDim() != 2)
                return false;
        }
        return dimension == 2 && points.length == 3;
    }
}
