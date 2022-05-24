package com.example.currencyrate.requester;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Service
@Component
public class NBURequesterImpl implements NBURequester{

    private static final Logger logger = Logger.getLogger(NBURequesterImpl.class);

    @Override
    @Cacheable("ratesFromString")
    public String getStringFromXml(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response != null) {
                return response.body();
            }
        } catch (IOException | InterruptedException e) {
            logger.error(e.getStackTrace());
        }
        return null;
    }
}
