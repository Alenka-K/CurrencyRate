package com.example.currencyrate.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;


@Configuration
@PropertySource(value = {"classpath:application.properties"})
@EnableCaching
public class NBUConfigImpl implements NBUConfig{

    public static final String DATE_FORMAT = "yyyyMMdd";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    @Value("${nbu.url}")
    private String url;

    @Override
    public URI getUrlOnDate(LocalDate date) {
        return UriComponentsBuilder.fromHttpUrl(url)
                .path("exchange")
                .queryParam("date", DATE_FORMATTER.format(date))
                .build()
                .toUri();
    }

    @Bean
    @Override
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("ratesFromString"),
                new ConcurrentMapCache("listRates"),
                new ConcurrentMapCache("rate")));
        return cacheManager;
    }
}
