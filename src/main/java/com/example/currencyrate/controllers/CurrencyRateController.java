package com.example.currencyrate.controllers;


import com.example.currencyrate.services.NBUService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;


@RestController
@RequestMapping(path = "/controller")
public class CurrencyRateController {

    private final NBUService nbuService;

    public CurrencyRateController(NBUService nbuService) {
        this.nbuService = nbuService;
    }


    @GetMapping("/NBU/{currency}/{date}")
    public ResponseEntity<?> getRate(@PathVariable("currency") String currency,
                                     @DateTimeFormat(pattern = "dd-MM-yyyy") @PathVariable("date") LocalDate date){
        if (date.isAfter(LocalDate.now())) return ResponseEntity.ok("Date does not exist!");
        float rate = nbuService.getCurrencyRate(currency, date);
        if(rate == 0.0f) return ResponseEntity.ok("Currency code does not exist!");
        return ResponseEntity.ok(rate);
        }
}
