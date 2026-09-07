package currency;

import currency.Repository.CurrencyRepository;
import currency.dto.CoindeskCurrency;
import currency.dto.CoindeskResponse;
import currency.dto.CoindeskTime;
import currency.dto.CryptoResponse;
import currency.entity.Currency;
import currency.service.CoindeskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

// 幣別轉換UnitTest
@ExtendWith(MockitoExtension.class)
public class CoindeskServiceTest {

    @Mock
    private CurrencyRepository currencyRepository;

    @InjectMocks
    private CoindeskService coindeskService;

    @Test
    void convertCoindeskResponse() {
        // Time
        CoindeskTime time = new CoindeskTime();
        time.setUpdatedISO(
                "2024-09-02T07:07:20+00:00"
        );

        // Currency
        CoindeskCurrency usd = new CoindeskCurrency();
        usd.setCode("USD");
        usd.setRate_float(57756.2984);


        // Coindesk Response
        CoindeskResponse response = new CoindeskResponse();
        response.setTime(time);

        HashMap<String, CoindeskCurrency> bpi = new HashMap<>();
        bpi.put("USD", usd);

        response.setBpi(bpi);

        // DB Data
        Currency currency = new Currency();
        currency.setCurrencyCode("USD");
        currency.setCurrencyName("美元");

        when(currencyRepository.findByCurrencyCode("USD"))
                .thenReturn(Optional.of(currency));

        // execute
        CryptoResponse result = coindeskService.convertCoindeskToResponse(response);

        // Assertions
        assertEquals("2024/09/02 15:07:20", result.getUpdateTime());

        assertEquals(1, result.getCurrencies().size());

        assertEquals("USD", result.getCurrencies().get(0).getCurrencyCode());

        assertEquals("美元", result.getCurrencies().get(0).getCurrencyName());

        assertEquals(57756.2984, result.getCurrencies().get(0).getRate());
    }
}
