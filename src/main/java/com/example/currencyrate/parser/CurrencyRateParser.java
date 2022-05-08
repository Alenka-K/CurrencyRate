package com.example.currencyrate.parser;


import com.example.currencyrate.model.CurrencyRate;
import java.util.List;



public interface CurrencyRateParser {

    List<CurrencyRate> parse(String ratesAsString);
}
