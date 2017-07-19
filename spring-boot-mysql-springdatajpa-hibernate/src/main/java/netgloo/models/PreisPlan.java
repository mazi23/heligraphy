package netgloo.models;

/**
 * Created by mazi on 04.05.17.
 */
public enum PreisPlan {
    BASIC(239),STANDARD(199),PREMIUM(399),PROFESSIONAL(599);

    private double value;

    private PreisPlan(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }


}
