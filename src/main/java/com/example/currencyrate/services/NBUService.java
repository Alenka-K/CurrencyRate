package com.example.currencyrate.services;

import com.example.currencyrate.NBUConfig;
import com.example.currencyrate.model.CurrencyRate;
import com.example.currencyrate.parser.CurrencyRateParser;
import com.example.currencyrate.requester.NBURequester;
import org.ehcache.Cache;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class NBUService implements CurrencyRateService {

    public static final String DATE_FORMAT = "yyyyMMdd";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);


    private final NBURequester nbuRequester;
    private final CurrencyRateParser currencyRateParser;
    private final NBUConfig nbuConfig;
    private final Cache<LocalDate, List<CurrencyRate>> currencyRateCache;

    public NBUService(NBURequester nbuRequester, CurrencyRateParser currencyRateParser, NBUConfig nbuConfig, Cache currencyRateCache) {
        this.nbuRequester = nbuRequester;
        this.currencyRateParser = currencyRateParser;
        this.nbuConfig = nbuConfig;
        this.currencyRateCache = currencyRateCache;
    }

    @Override
    public String getId() {
        return "NBU";
    }

    @Override
    public float getCurrencyRate(String currencyCode, LocalDate date) {
        List<CurrencyRate> rates = currencyRateCache.get(date);
        if (rates == null) {
            String urlWithParams = String.format(nbuConfig.getUrl() + DATE_FORMATTER.format(date));
            String ratesAsXml = nbuRequester.getStringFromXml(urlWithParams);
            rates = currencyRateParser.parse(ratesAsXml);
            currencyRateCache.put(date, rates);
        }
        return rates.stream().filter(currency-> currencyCode.equals(currency.getCurrencyCode())).findFirst().get().getValue();
    }
}
