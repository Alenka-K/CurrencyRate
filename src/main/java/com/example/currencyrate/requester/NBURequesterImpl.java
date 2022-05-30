package com.example.currencyrate.requester;

import com.example.currencyrate.config.NBUConfig;
import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;


@Service
public class NBURequesterImpl implements NBURequester{

    private static final Logger logger = Logger.getLogger(NBURequesterImpl.class);

    private final NBUConfig nbuConfig;

    public NBURequesterImpl(NBUConfig nbuConfig) {
        this.nbuConfig = nbuConfig;
    }

    @Override
    @Cacheable("ratesFromString")
    public String getStringFromXml(LocalDate date) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(nbuConfig.getUrlOnDate(date)).build();
        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response != null) {
                return response.body();
            }
        } catch (IOException | InterruptedException e) {
            logger.error("String from Xml was not received", e);
        }
        return null;
    }
}
