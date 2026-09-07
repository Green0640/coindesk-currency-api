package currency.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CoindeskCurrency {

    private String code;

    private String symbol;

    private String rate;

    private String description;

    private Double rate_float;
}
