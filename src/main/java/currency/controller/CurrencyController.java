package currency.controller;

import currency.entity.Currency;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import currency.service.CurrencyService;

import java.util.List;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    //全部查詢
    @GetMapping
    public List<Currency> findAll() {
        return currencyService.findAll();
    }

    //單筆查詢
    @GetMapping("/{id}")
    public Currency findById(@PathVariable Long id) {
        return currencyService.findById(id);
    }

    // 新增
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Currency create(@RequestBody Currency currency) {
        return currencyService.create(currency);
    }

    // 修改
    @PutMapping("/{id}")
    public Currency update(
            @PathVariable Long id,
            @RequestBody Currency currency) {

        return currencyService.update(id, currency);
    }

    // 刪除
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        currencyService.delete(id);
    }
}
