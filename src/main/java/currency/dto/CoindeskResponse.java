package currency.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class CoindeskResponse {

    private CoindeskTime time;

    private String disclaimer;

    private String charName;

    private Map<String, CoindeskCurrency> bpi;
}
