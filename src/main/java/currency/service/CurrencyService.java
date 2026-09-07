package currency.service;

import currency.Repository.CurrencyRepository;
import currency.entity.Currency;
import currency.exception.CurrencyNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    // 查詢全部
    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    // 查詢單筆
    public Currency findById(Long id) {
        return currencyRepository.findById(id)
                .orElseThrow(() -> new CurrencyNotFoundException("Currency not found"));
    }


    // 新增
    public Currency create(Currency currency) {
        return currencyRepository.save(currency);
    }

    // 修改
    public Currency update(Long id, Currency currency) {
        Currency existing = findById(id);

        existing.setCurrencyCode(currency.getCurrencyCode());
        existing.setCurrencyName(currency.getCurrencyName());

        return currencyRepository.save(existing);
    }

    // 刪除
    public void delete(Long id) {
        Currency existing = findById(id);
        currencyRepository.delete(existing);
    }
}
