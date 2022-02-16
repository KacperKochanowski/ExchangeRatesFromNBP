package nbpApi;

public class Rate {
    private String currency;
    private String code;
    private double mid;
    private double bid;
    private double ask;

    public String getCurrency() {
        return currency;
    }

    public String getCode() {
        return code;
    }

    public double getMid() {
        return mid;
    }

    public double getBid() {
        return bid;
    }

    public double getAsk() {
        return ask;
    }
}
