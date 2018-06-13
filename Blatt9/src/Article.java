import java.security.PublicKey;

public class Article {
    public int value, weight;

    public Article(int value, int weight) {
        if (value <= 0) {
            throw new IllegalArgumentException("Value has to be >= 0");
        }

        if (weight <= 0) {
            throw new IllegalArgumentException("Weight has to be >= 0");
        }

        this.value = value;
        this.weight = weight;

    }

    public double getRatio() {
        return value/weight;
    }
}
