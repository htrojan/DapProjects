public class Job {
    private int duration, deadline;


    public int getDuration() {
        return duration;
    }

    public int getDeadline() {
        return deadline;
    }

    public Job(int duration, int deadline) {

        if (duration > deadline) {
            throw new IllegalArgumentException("The duration itself lies beyond the deadline");
        }
        
        this.duration = duration;
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[" + duration + ", " + deadline + "]";
    }
}
