package com.example.currencyrate;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import java.time.LocalDate;
import java.util.List;


@Configuration
@PropertySource(value = {"classpath:application.properties"})
@EnableCaching
public class NBUConfig {
    private final CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);

    @Value("${nbu.url}")
    private String url;

    public String getUrl() {
        return url;
    }

    @Bean
    public Cache<LocalDate, List> currencyRateCache(@Value("${cache.size}") int cacheSize) {
        return cacheManager.createCache("currencyRateCache",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(LocalDate.class, List.class , ResourcePoolsBuilder.heap(cacheSize)).build());
    }
}
