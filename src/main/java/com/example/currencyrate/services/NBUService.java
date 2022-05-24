package com.example.currencyrate.services;

import com.example.currencyrate.config.NBUConfig;
import com.example.currencyrate.model.CurrencyRate;
import com.example.currencyrate.parser.CurrencyRateParser;
import com.example.currencyrate.requester.NBURequester;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Service
@Component
public class NBUService implements CurrencyRateService {

    public static final String DATE_FORMAT = "yyyyMMdd";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);


    private final NBURequester nbuRequester;
    private final CurrencyRateParser currencyRateParser;
    private final NBUConfig nbuConfig;

    public NBUService(NBURequester nbuRequester, CurrencyRateParser currencyRateParser, NBUConfig nbuConfig) {
        this.nbuRequester = nbuRequester;
        this.currencyRateParser = currencyRateParser;
        this.nbuConfig = nbuConfig;
    }

    @Override
    public String getId() {
        return "NBU";
    }

    @Override
    @Cacheable("rate")
    public float getCurrencyRate(String currencyCode, LocalDate date) {
            String urlWithParams = nbuConfig.getUrl() + DATE_FORMATTER.format(date);
            String ratesAsXml = nbuRequester.getStringFromXml(urlWithParams);
            List<CurrencyRate> rates = currencyRateParser.parse(ratesAsXml);
        Optional<CurrencyRate> rate = rates.stream().filter(currency-> currencyCode.equals(currency.getCurrencyCode())).findFirst();
        return rate.map(CurrencyRate::getValue).orElse(0.0f);
    }
}
