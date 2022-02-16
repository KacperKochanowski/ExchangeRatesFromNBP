package nbpApi;

import java.util.List;

public class RateTable {
    private String table;
    private String no;
    private String effectiveDate;
    private List<Rate> rates;
    private String tradingDate;


    public String getTable() {
        return table;
    }

    public String getNo() {
        return no;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public String getTradingDate() {
        return tradingDate;
    }
}