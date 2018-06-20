
public class TowerHanoi {

    public static void move(int quantity, char start, char help, char target) {
        if (quantity <= 1) {
            System.out.println("Verschiebe oberste Scheibe von " + start + " nach " + target);
            return;
        }

        //Target und Help vertauschen, da auf den Hilfstapel verschoben werden soll, bis die unterste Scheibe
        //oben liegt
        move(quantity -1, start, target, help);
        //Alle Scheiben bis auf eine auf den Hilfsstapel verschieben.
        //Eigentliche Scheibe verschieben
        System.out.println("Verschiebe oberste Scheibe von " + start + " nach " + target);

        //Jetzt restlich Scheiben rekursiv zum Ziel verschieben
        //Da alle Scheiben jetzt auf dem Hilfstapel liegen und Start leer ist, müssen
        //die Stapel vertauscht wrden
        move(quantity -1, help, start, target);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: TowerHanoi n");
        }

        int n;

        try {
            n = Integer.parseInt(args[0]);

            if(n <= 0) //Bad code, einfach weg schauen
                throw new NumberFormatException("lower than zero");

        } catch (NumberFormatException e) {
            System.out.println("Only positive integers accepted as input");
            return;
        }

        move(4, 'A', 'B', 'C');
    }
}
