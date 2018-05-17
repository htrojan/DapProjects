import java.util.Arrays;

public class MuenzWechsel {
    private static final int[] euro = {200, 100, 50, 20, 10, 5, 2, 1};
    private static final int[] alt = {200, 100, 50, 20, 10, 5, 4, 2, 1};

    public static void main(String[] args) {
        if (args.length != 2) {
            printUsage();
            return;
        }

        int[] waehrung;

        switch (args[0]) {
            case ("euro"):
                waehrung = euro;
                break;
            case ("alternative"):
                waehrung = alt;
                break;
            default:
                //Mist eingegeben
                System.out.println("Please use valid variable");
                printUsage();
                return;
        }

        int geld;

        try {
            geld = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Not a valid number");
            printUsage();
            return;
        }

        if (geld <= 0) {
            System.out.println("wechselgeld must be >= 0");
            return;
        }

        //Ausgabe
        int[] result = change(geld, waehrung);
        System.out.println("b = " + geld);
        System.out.println("Ihr Wechselgeld:");
        System.out.println(Arrays.toString(waehrung));
        System.out.println(Arrays.toString(result));

    }

    public static int[] change(int geld, int[] waehrung) {
        //Muss die gleiche Länge haben
        int result[] = new int[waehrung.length];
        //Über alle Werte iterieren
        //Inv. alle Wechselwerte bis waehrung[i-1] wurden gesetzt
        for (int i = 0; i < waehrung.length; i++) {
            result[i] = geld/waehrung[i]; //Kommazahlen verfallen
            //schon gewechseltes Geld abziehen
            geld -= result[i] * waehrung[i];
        }
        return result;
    }

    private static void printUsage() {
        System.out.println("MuenzWechsel euro|alternative wechselgeld");
    }
}
