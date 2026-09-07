package currency.service;

import currency.Repository.CurrencyRepository;
import currency.dto.CoindeskCurrency;
import currency.dto.CoindeskResponse;
import currency.dto.CryptoCurrency;
import currency.dto.CryptoResponse;
import currency.entity.Currency;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CoindeskService {
    private final CurrencyRepository currencyRepository;
    private final RestTemplate restTemplate;
    private static final String COINDESK_URL = "https://kengp3.github.io/blog/coindesk.json";

    public CoindeskService(CurrencyRepository currencyRepository, RestTemplate restTemplate) {
        this.currencyRepository = currencyRepository;
        this.restTemplate = restTemplate;
    }

    public CryptoResponse getCoindeskRate() {
        CoindeskResponse coindeskResponse = restTemplate.getForObject(COINDESK_URL, CoindeskResponse.class);
        return convertCoindeskToResponse(coindeskResponse);
    }

    public CryptoResponse convertCoindeskToResponse(CoindeskResponse response) {
        CryptoResponse cryptoResponse = new CryptoResponse();

        String updateTime = convertTime(response.getTime().getUpdatedISO());
        cryptoResponse.setUpdateTime(updateTime);

        List<CryptoCurrency> currencies = new ArrayList<>();

        for(Map.Entry<String, CoindeskCurrency> entry : response.getBpi().entrySet()) {
            String currencyCode = entry.getKey();
            CoindeskCurrency coindeskCurrency = entry.getValue();

            Optional<Currency> currency = currencyRepository.findByCurrencyCode(currencyCode);

            if(!currency.isPresent()) {
                continue;
            }

            CryptoCurrency cryptoCurrency = new CryptoCurrency();
            cryptoCurrency.setCurrencyCode(currencyCode);
            cryptoCurrency.setCurrencyName(currency.get().getCurrencyName());
            cryptoCurrency.setRate(coindeskCurrency.getRate_float());
            currencies.add(cryptoCurrency);
        }

        cryptoResponse.setCurrencies(currencies);

        return cryptoResponse;
    }

    private String convertTime(String time) {
        OffsetDateTime dateTime = OffsetDateTime.parse(time);

        return dateTime
                .atZoneSameInstant(ZoneId.of("Asia/Taipei"))
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }
}
