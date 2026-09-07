package currency.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CryptoCurrency {

    private String currencyCode;

    private String currencyName;

    private Double rate;
}
