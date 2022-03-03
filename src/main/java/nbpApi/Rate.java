package nbpApi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rate {
    private String currency;
    private String code;
    private double mid;
    private double bid;
    private double ask;

    @Override
    public String toString() {
        return code;
    }

    public double getMid() {
        if (bid <= 0 && ask <= 0) {
            return mid;
        } else {
            return (ask + bid) / 2;
        }
    }
}