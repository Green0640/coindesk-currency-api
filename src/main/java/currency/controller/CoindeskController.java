package currency.controller;

import currency.dto.CryptoResponse;
import currency.service.CoindeskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/coindesk")
public class CoindeskController {
    private final CoindeskService coindeskService;

    public CoindeskController(CoindeskService coindeskService) {
        this.coindeskService = coindeskService;
    }

    @GetMapping
    public CryptoResponse getCryptoRate() {
        return coindeskService.getCoindeskRate();
    }
}
