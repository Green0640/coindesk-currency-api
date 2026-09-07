package currency;

import currency.dto.CoindeskResponse;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CoindeskApiTest {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String url = "https://kengp3.github.io/blog/coindesk.json";
    @Test
    void callCoindeskApi() {
        CoindeskResponse response = restTemplate.getForObject(url, CoindeskResponse.class);

        assertNotNull(response);

        assertNotNull(response.getTime());
        assertNotNull(response.getTime().getUpdatedISO());

        assertNotNull(response.getBpi());

        assertTrue(response.getBpi().containsKey("USD"));
        assertTrue(response.getBpi().containsKey("GBP"));
        assertTrue(response.getBpi().containsKey("EUR"));

        assertNotNull(response.getBpi()
                        .get("USD")
                        .getRate_float()
        );

        System.out.println("===== Coindesk API Response =====");
        System.out.println(response);
    }
}