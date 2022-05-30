package com.example.currencyrate.services;

import com.example.currencyrate.model.CurrencyRate;
import com.example.currencyrate.parser.CurrencyRateParser;
import com.example.currencyrate.requester.NBURequester;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class NBUService implements CurrencyRateService {

    private final NBURequester nbuRequester;
    private final CurrencyRateParser currencyRateParser;


    public NBUService(NBURequester nbuRequester, CurrencyRateParser currencyRateParser) {
        this.nbuRequester = nbuRequester;
        this.currencyRateParser = currencyRateParser;
    }

    @Override
    public String getId() {
        return "NBU";
    }

    @Override
    @Cacheable("rate")
    public float getCurrencyRate(String currencyCode, LocalDate date) {
            String ratesAsXml = nbuRequester.getStringFromXml(date);
            List<CurrencyRate> rates = currencyRateParser.parse(ratesAsXml);
        Optional<CurrencyRate> rate = rates.stream().filter(currency-> currencyCode.equals(currency.getCurrencyCode())).findFirst();
        return rate.map(CurrencyRate::getValue).orElse(0.0f);
    }
}
