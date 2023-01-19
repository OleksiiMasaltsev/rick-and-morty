package ua.masaltsev.rickandmorty.service;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class HttpClient {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public <T> T get(String url, Class<T> clazz) {
        HttpGet request = new HttpGet(url);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            // Get HttpResponse Status
//            System.out.println(response.getStatusLine().toString());
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                System.out.println(result);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException("Can't fetch from url: " + url, e);
        }
        return null;
    }
}
