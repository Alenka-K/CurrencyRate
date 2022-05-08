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
        float rate = nbuService.getCurrencyRate(currency, date);
        return ResponseEntity.ok(rate);
        }
}
