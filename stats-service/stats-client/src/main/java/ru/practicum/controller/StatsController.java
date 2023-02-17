package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.client.StatsClient;
import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.ViewStats;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsController {

    private final StatsClient statsClient;

    @PostMapping("/hit")
    public ResponseEntity<EndpointHit> saveEndpointHit(@RequestBody EndpointHit endpointHit)
            throws IOException, InterruptedException {
        log.info("POST request to /hit");
        EndpointHit savedEndpointHit = statsClient.save(endpointHit);
        return ResponseEntity.ok(savedEndpointHit);
    }

    @GetMapping("/stats")
    public ResponseEntity<List<ViewStats>> getStats(
            @RequestParam(name = "start") String start,
            @RequestParam(name = "end") String end,
            @RequestParam(name = "uris") List<String> uris,
            @RequestParam(name = "unique", required = false, defaultValue = "false") boolean unique)
            throws IOException, InterruptedException {
        log.info("GET request to /stats?start={}&end={}&uris={}&unique={}", start, end, uris, unique);
        List<ViewStats> stats = statsClient.getStats(start, end, uris, unique);
        return ResponseEntity.ok(stats);
    }
}
