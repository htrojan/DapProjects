import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class ConvexHull {
    public List<Point> simpleConvex(Point[] points) {


        for (Point p1 : points) {
            for (Point p2 : points) {
                //wird false wenn ein Punkt links der Gerade liegt
                boolean valid = true;
                //p1, p2 definieren eine Gerade
                for (Point q : points) {
                    //Es wird getestet, ob alle Punkte q rechts von der Geraden liegen
                    if (isLeftOfLine(q, p1, p2))
                        valid = false;
                }

                if (valid) {
                    //TODO: In doppelt verkettete Liste einfügen
                }
            }
        }

        //Mach mal, Zukunfts-Ich
        throw new NotImplementedException();
    }

    /**
     * Prüft, ob der gegebene Punkt links von der gegebenen Geraden liegt
     *
     * @param q  Der zu prüfende Punkt
     * @param p1 Der Startpunkt der Gerade
     * @param p2 Der Endpunkt der Gerade
     */
    private boolean isLeftOfLine(Point q, Point p1, Point p2) {
        //Gerade in Normalenform bringen
        //Richtingsvektor r = p2 - p1 (zeigt auf r2)
        Point r = p2.minus(p1);
        //Normalenvektor von (x, y) => (-y, x), zeigt von der linken Seite der Gerade weg
        Point normal = new Point(2, -r.get(1), r.get(0));
        //Der Abstand zum Koordinatenursprung in der Normalenvorm
        //g: x * n = d
        double d = normal.scalar(p1);
        //Der normierte Abstand vom Punkt zur Gerade
        double distance = normal.scalar(q) - d;

        //Bei distance > 0 liegt der Punkt auf der Seite der Gerade, in deren Richtung der Normalenvektor zeigt
        //--> distance > 0 => Punkt liegt links von der Geraden
        return distance > 0;
    }
}
