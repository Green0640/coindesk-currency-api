package currency.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CryptoResponse {

    private String updateTime;

    private List<CryptoCurrency> currencies;
}
