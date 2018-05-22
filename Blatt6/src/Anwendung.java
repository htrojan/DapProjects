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

    /**
     * @param jobs sortiert nach der Deadline.
     * @return Die Startzeitpunkte der Jobs aus dem Eingabearray
     */
    public static int[] latenessScheduling(ArrayList<Job> jobs) {
        int startzeit = 0;
        int[] result = new int[jobs.size()];

        //Die kleinste Deadline zuerst. Da bereits sortiert "trivial"
        for (int i = 0; i < jobs.size(); i++) {
            result[i] = startzeit;
            startzeit += jobs.get(i).getDuration();
        }

        return result;
    }


    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Wrong count of arguments");
            printUsage();
            return;
        }

        BufferedReader file;
        try {
            URL loader = ClassLoader.getSystemResource(args[1]);
            file = new BufferedReader(new FileReader(loader.getFile()));
        } catch (Exception e) {
            System.out.println("This file could not be read / does not exist");
            return;
        }


        //Parse arguments lateness | scheduling
        switch (args[0]) {
            case "lateness":
                ArrayList<Job> jobs = parseFileTo(file, Job::new);
                printReadInfo(args[1], jobs);
                printLatenessScheduling(jobs);
                break;

            case "interval":
                ArrayList<Interval> intervals = parseFileTo(file, Interval::new);
                printReadInfo(args[1], intervals);
                printIntervalScheduling(intervals);
                break;
            default:
                System.out.println("Argument one must be interval or lateness");
                printUsage();
                return;
        }

    }

    private static void printReadInfo(String arg, ArrayList intervals) {
        System.out.println("Lese Datei" + arg);
        System.out.println("Es wurden " + intervals.size() + " Zeilen mit folgendem Inhalt eingelesen:");
        System.out.println("[" + Arrays.toString(intervals.toArray()) + "]\n");
    }

    interface IParserConverter<T> {
        T convertTo(int i1, int i2);
    }

    private static <T> ArrayList<T> parseFileTo(BufferedReader file, IParserConverter<T> conv) {
        String line;
        ArrayList<T> intervals = new ArrayList<>();

        try {
            while ((line = file.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, ", ");
                int start = Integer.parseInt(tokenizer.nextToken());
                int end = Integer.parseInt(tokenizer.nextToken());
                //Hier zu dem geforderten Objekt konvertieren (Job oder Interval)
                T interval = conv.convertTo(start, end);
                intervals.add(interval);
            }

            //IllegalArgumentException catches NumberformatException as it's a subclass
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error while reading file");
            return null;
        }
        return intervals;
    }

    private static void printLatenessScheduling(ArrayList<Job> jobs) {
        //Sortieren um Invariante zu erfüllen
        jobs.sort(Comparator.comparingInt(Job::getDeadline));

        System.out.println("Sortiert [time, deadline]:");
        System.out.println("[" + Arrays.toString(jobs.toArray()) + "]\n");

        int[] startTimes = latenessScheduling(jobs);
        System.out.println("Berechnetes Latenessscheduling");
        System.out.println("[" + Arrays.toString(startTimes) + "]");
    }

    private static void printIntervalScheduling(ArrayList<Interval> intervals) {
        //Fulfill invariant. Sort for interval end
        intervals.sort(Comparator.comparingInt(Interval::getEnd));

        System.out.println("Sortiert:");
        System.out.println("[" + Arrays.toString(intervals.toArray()) + "]\n");

        ArrayList<Interval> scheduled = intervalScheduling(intervals);

        System.out.println("Berechnetes Scheduling:");
        System.out.println("[" + Arrays.toString(scheduled.toArray()) + "]\n");
    }

    private static void printUsage() {
        System.out.println("Usage: Anwendung interval|lateness file");
    }
}
