package com.example.currencyrate.services;


import java.time.LocalDate;


public interface CurrencyRateService {

    String getId();
    float getCurrencyRate (String currencyCode, LocalDate date);


}
