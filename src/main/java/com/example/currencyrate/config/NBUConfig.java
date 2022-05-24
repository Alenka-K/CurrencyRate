package com.example.currencyrate.config;

import org.springframework.cache.CacheManager;


public interface NBUConfig {

    String getUrl();
    CacheManager cacheManager();
}
