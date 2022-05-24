package com.example.currencyrate.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Arrays;


@Configuration
@PropertySource(value = {"classpath:application.properties"})
@EnableCaching
public class NBUConfigImpl implements NBUConfig{

    @Value("${nbu.url}")
    private String url;

    @Override
    public String getUrl() {
        return url;
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
