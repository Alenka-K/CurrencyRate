package com.example.currencyrate.controllers;


import com.example.currencyrate.services.CurrencyRateService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;


@RestController
@RequestMapping
public class CurrencyRateController {

    private final CurrencyRateService currencyRateService;

    public CurrencyRateController(CurrencyRateService currencyRateService) {
        this.currencyRateService = currencyRateService;
    }

    @GetMapping("/")
    public String menu() {
        return "Enter currency code and date (day-month-year). Example: /EUR/01-01-2022)";
    }

    @GetMapping("/{currency}/{date}")
    public ResponseEntity<?> getRate(@PathVariable("currency") String currency,
                                     @DateTimeFormat(pattern = "dd-MM-yyyy") @PathVariable("date") LocalDate date){
        if (date.isAfter(LocalDate.now())) return ResponseEntity.ok("Date does not exist!");
        float rate = currencyRateService.getCurrencyRate(currency, date);
        if(rate == 0.0f) return ResponseEntity.ok("Currency code does not exist!");
        return ResponseEntity.ok(rate);
        }
}
