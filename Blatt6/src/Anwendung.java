import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Anwendung {

    /**
     * @param intervals Die Liste der Intervalle. Müssen nach Ende aufsteigend sortiert sein
     * @return Die Liste der angenommenen Intervalle in aufsteigender Reihenfolge
     */
    public static ArrayList<Interval> intervalScheduling(ArrayList<Interval> intervals) {

        ArrayList<Interval> result = new ArrayList<>();
        result.add(intervals.get(0)); //Das erste Element ist automatisch das mit dem schnellsten Ende, da sortiert

        int j = 0; //Der Index, der auf das zuletzt eingeordnete Intervall zeigt
        //Läuft über die Enden der Intervalle


        for (int i = 1; i < intervals.size(); i++) { //i läuft über die Intervallanfänge
            if (intervals.get(i).getStart() >= intervals.get(j).getEnd()) {
                //Der erste wieder gültige Intervall
                result.add(intervals.get(i));
                //letzten Intervall setzen
                j = i;
            }
        }

        return result;
    }


    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("No file given");
            printUsage();
            return;
        }

        BufferedReader file;
        try {
            URL loader = ClassLoader.getSystemResource(args[0]);
            file = new BufferedReader(new FileReader(loader.getFile()));
        } catch (Exception e) {
            System.out.println("This file could not be read / does not exist");
            return;
        }

        String line;
        ArrayList<Interval> intervals = new ArrayList<>();

        try {
            while ((line = file.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, ", ");
                int start = Integer.parseInt(tokenizer.nextToken());
                int end = Integer.parseInt(tokenizer.nextToken());
                Interval interval = new Interval(start, end);
                intervals.add(interval);
            }

            //IllegalArgumentException catches NumberformatException as it's a subclass
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error while reading file");
            return;
        }


        System.out.println("Lese Datei" + args[0]);
        System.out.println("Es wurden " + intervals.size() + " Zeilen mit folgendem Inhalt eingelesen:");
        System.out.println("[" + Arrays.toString(intervals.toArray()) + "]\n");

        //Fulfill invariant. Sort for interval end
        intervals.sort(Comparator.comparingInt(Interval::getEnd));

        System.out.println("Sortiert:");
        System.out.println("[" + Arrays.toString(intervals.toArray()) + "]\n");

        ArrayList<Interval> scheduled = intervalScheduling(intervals);

        System.out.println("Berechnetes Scheduling:");
        System.out.println("[" + Arrays.toString(scheduled.toArray()) + "]\n");
    }

    private static void printUsage() {
        System.out.println("Usage: Anwendung file");
    }
}
