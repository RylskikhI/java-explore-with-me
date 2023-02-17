package ru.practicum.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.ViewStats;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class StatsClient {
    private final String url;
    private final HttpClient httpClient;

    public StatsClient(@Value("${stats-client.server.url}") String url) {
        this.url = url;
        this.httpClient = HttpClient.newHttpClient();
    }

    public EndpointHit save(EndpointHit endpointHit) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/hit"))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(endpointHit)))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            log.info("Send post request /hit statusCode={}", response.statusCode());
            return new ObjectMapper().readValue(response.body(), EndpointHit.class);
        } else {
            log.error("Send post request /hit statusCode={}", response.statusCode());
            throw new RuntimeException("Request failed with status code " + response.statusCode());
        }
    }

    public List<ViewStats> getStats(String start, String end, List<String> uris, Boolean unique)
            throws IOException, InterruptedException {
        String uriParams = String.format("?start=%s&end=%s&uris=%s&unique=%s", start, end,
                String.join(",", uris), unique);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/stats" + uriParams))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            log.info("Send get request /stats?start={}&end={}&uris={}&unique={} statusCode={}",
                    start, end, uris, unique, response.statusCode());
            return Arrays.asList(new ObjectMapper().readValue(response.body(), ViewStats[].class));
        } else {
            log.error("Send get request /stats?start={}&end={}&uris={}&unique={} statusCode={}",
                    start, end, uris, unique, response.statusCode());
            throw new RuntimeException("Request failed with status code " + response.statusCode());
        }
    }
}
