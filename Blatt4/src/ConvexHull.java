import java.util.List;
import java.util.Random;
import java.util.LinkedList;

public class ConvexHull {
	
    public List<Point> simpleConvex(Point[] points) {
    	
    	List<Point> l = new LinkedList<Point>();
    	
        for (Point p1 : points)
        for (Point p2 : points){ 
        	
        	//Nur verschiedene Punkte
        	if(p1 == p2)
        		continue;
        	
            //wird false wenn ein Punkt links der Gerade liegt
            boolean valid = true;
            //p1, p2 definieren eine Gerade
            for (Point q : points) {
            	//Nicht fuer p1 und p2 testen
            	if(q == p1 || q== p2)
            		continue;
            	
                //Es wird getestet, ob alle Punkte q rechts von der Geraden liegen
                if (isLeftOfLine(q, p1, p2)) {
                    valid = false;
                    break; // einmal false immer false
                }
            }

            if (valid) {
            	int index = 0;
            	//eine Kante mit p1 wurde schon gefunden und eingetragen
				//Da jede Kante einzigartig ist und die Gerade die Richtung p1 -> p2 aufweist,
				//muss p2 hinter p1 hinzugefügt werden
            	if((index = l.indexOf(p1)) != -1) {
            		l.add(index + 1, p2); //Falls bei index + 1 schon ein Element sitzt wird der rechte Teil der Liste verschoben
            		//Eine Kante mit p2 wurde schon gefunden und eingetragen
            	}else if((index = l.indexOf(p2)) != -1) {
            		l.add(index, p1);
            	}else {
            		//Keine Kante mit einem der beiden Punkte wurde gefunden
            		l.add(p1);
            		l.add(p2);
            	}
            }
      
        }
        
        
        return l; 
    }

    /**
     * Prueft, ob der gegebene Punkt links von der gegebenen Geraden liegt
     *
     * @param q  Der zu pruefende Punkt
     * @param p1 Der Startpunkt der Gerade
     * @param p2 Der Endpunkt der Gerade
     */
    private boolean isLeftOfLine(Point q, Point p1, Point p2) {
        //Gerade in Normalenform bringen
        //Richtingsvektor r = p2 - p1 (zeigt auf p2)
        Point r = p2.minus(p1);
        //Normalenvektor von (x, y) => (-y, x), zeigt von der linken Seite der Gerade weg
        Point normal = new Point(2, -r.get(1), r.get(0));
        
      
        //Der Abstand zum Koordinatenursprung (um die Laenge von n gestreckt) in der Normalenform
        //g: x * n = d
        double d = normal.scalar(p1);
        //Der nicht-normierte (Um einen Faktor Laenge n) Abstand vom Punkt zur Gerade
        double distance = normal.scalar(q) - d;

        //Bei distance > 0 liegt der Punkt auf der Seite der Gerade, in deren Richtung der Normalenvektor zeigt
        //--> distance > 0 => Punkt liegt links von der Geraden
        return distance > 0;
    }
    
    
    public static void main(String[] args) {
    	Random r = new Random(); 
    	
    	//Indizes der Stellen, an dem das Dreieck eingefuegt wird.
    	int a = r.nextInt(1003), b = 0, c = 0;
    	do {
    		b = r.nextInt(1003);
    	}while(b == a);
    	do {
    		c = r.nextInt(1003);
    	}while(c == b || c == a);
    	
    	//Dreieck in die Liste einfuegen
    	Point[] points = new Point[1003];
    	points[a] = new Point(2, 10, 10); 
    	points[b] = new Point(2, 10, 100);
    	points[c] = new Point(2, 100, 10);
    	
    	for(int i = 0; i < 1003; i++)
    	{
    		//Nicht das 3 Eck aendern
    		if(i == a || i == b || i == c)
    			continue;
    		
    		//Punkt so machen, dass er im 3 Eck liegt. (Nicht jede stelle gleichwahrscheinlich...)
    		double x = r.nextDouble() * 90; 
    		double y = r.nextDouble() * (90-x); //Da Steigung von -1 -> Linear: y = (-1) * x + 90 bei Verschiebung
												//des Dreiecks auf den Koordinatenursprung für Beträge der Seiten
    		
    		points[i] = new Point(2, 10 + x, 10 + y); 
    	}
    	
    	
    	List<Point> ch = new ConvexHull().simpleConvex(points);
    	
    	for(Point p : ch)
    	{
    		System.out.println(p.toString());
    	}
    	
    }
}
