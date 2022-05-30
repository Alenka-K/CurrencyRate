package com.example.currencyrate.config;

import org.springframework.cache.CacheManager;

import java.net.URI;
import java.time.LocalDate;


public interface NBUConfig {

    URI getUrlOnDate(LocalDate date);

    CacheManager cacheManager();
}
