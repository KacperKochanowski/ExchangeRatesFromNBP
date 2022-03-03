package nbpApi;

import java.util.List;

public class RateTable {
    private java.lang.String table;
    private java.lang.String no;
    private java.lang.String effectiveDate;
    private java.lang.String tradingDate;
    private List<Rate> rates;

    public java.lang.String getTable() {
        return table;
    }

    public java.lang.String getNo() {
        return no;
    }

    public java.lang.String getEffectiveDate() {
        return effectiveDate;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public java.lang.String getTradingDate() {
        return tradingDate;
    }
}