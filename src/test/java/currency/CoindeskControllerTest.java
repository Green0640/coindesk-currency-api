package currency;

import currency.Repository.CurrencyRepository;
import currency.dto.CoindeskCurrency;
import currency.dto.CoindeskResponse;
import currency.dto.CoindeskTime;
import currency.entity.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class CoindeskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private CurrencyRepository currencyRepository;

    @Test
    void getCryptoRate() throws Exception {
        CoindeskResponse response = new CoindeskResponse();

        CoindeskTime time = new CoindeskTime();
        time.setUpdatedISO("2024-09-02T07:07:20+00:00");
        response.setTime(time);

        HashMap<String, CoindeskCurrency> bpi = new HashMap<>();
        CoindeskCurrency usd = new CoindeskCurrency();
        usd.setCode("USD");
        usd.setRate_float(57756.2984);
        bpi.put("USD", usd);
        response.setBpi(bpi);
        String url = "https://kengp3.github.io/blog/coindesk.json";

        when(restTemplate.getForObject(eq(url), eq(CoindeskResponse.class))).thenReturn(response);

        Currency currency = new Currency();
        currency.setCurrencyCode("USD");
        currency.setCurrencyName("美元");

        when(currencyRepository.findByCurrencyCode("USD")).thenReturn(Optional.of(currency));

        mockMvc.perform(get("/api/coindesk/crypto"))
                .andDo(org.springframework.test.web.servlet.result.MockMvcResultHandlers.print())

                // HTTP
                .andExpect(status().isOk())

                // 時間轉換
                .andExpect(jsonPath("$.updateTime").value("2024/09/02 15:07:20"))

                // 幣別
                .andExpect(jsonPath("$.currencies[0].currencyCode").value("USD"))

                // 中文名稱
                .andExpect(jsonPath("$.currencies[0].currencyName").value("美元"))

                // 匯率
                .andExpect(jsonPath("$.currencies[0].rate").value(57756.2984));
    }
}
