public class Interval {
    private int start, end;

    public Interval(int start, int end) {
        if (start > end)
            throw new IllegalArgumentException("end has to be >= start");

        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "[" + start + ", " + end + "]";
    }
}
