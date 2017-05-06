package netgloo.models;

/**
 * Created by mazi on 04.05.17.
 */
public enum PreisPlan {
    BASIC(280),STANDART(350),PREMIUM(500),PROFESSIONAL(700);

    private double value;

    private PreisPlan(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }


}
