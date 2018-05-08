import java.util.Arrays;
import java.util.Random;

public class Application {
    public static void main(String[] args) {

        Point[] points;

        if (args.length == 6) {
            //Punkte Ueber die Kommandozeile annehmen
        	//Und Prueffen ob alles so klappt...
        	try {
        		points = parsePoints(args);
        	}catch(NumberFormatException e) {
        		System.out.println("Can't decifer numbers...");
        		printUsage();
        		return;
        	}
        } else if (args.length == 0) {
            points = randomPoints();
        } else {
            //Falsche Eingabe
            System.out.println("Wrong parameter count");
            printUsage();
            return;
        }

        System.out.println("Used points:\n" + Arrays.toString(points) + "\n");
        Triangle triangle = new Triangle(points);
        System.out.println("Perimeter: " + triangle.perimeter());
    }

    private static Point[] randomPoints() {
        final int limit = 1000; //Too cool?
        final Random random = new Random();

        Point[] points = new Point[3];
        //bis 3 da 3 Punkte erzeugt werden muessen
        for (int i = 0; i < 3; i++) {
            //Einen Wertebereich von [0; 2000] durch Verschiebung um -1000
            //auf [-1000; +1000] veraendern
            double x = random.nextDouble() * limit * 2 - 1000;
            double y = random.nextDouble() * limit * 2 - 1000;

            points[i] = new Point(2, x, y);
        }
        return points;
    }

    private static Point[] parsePoints(String[] coords) {
        Point[] points = new Point[3];
        //In 2er Schritten inkrementieren, da immer 2 Koordinaten einen Punkt ausmachen
        for (int i = 0; i < 6; i += 2) {
            double x = Double.parseDouble(coords[i]);
            double y = Double.parseDouble(coords[i + 1]);
            //Da bei Integern abgerundet kommt der richtige Index raus
            points[i / 2] = new Point(2, x, y);
        }

        return points;
    }

    private static void printUsage() {
        System.out.println("Usage: Application [x1, y1, x2, y2, x3, y3]");
    }
}
