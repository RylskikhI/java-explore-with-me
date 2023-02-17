package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.ViewStats;
import ru.practicum.mapper.StatsMapper;
import ru.practicum.model.Stats;
import ru.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;

    @Override
    public List<ViewStats> getStats(String start, String end, List<String> uris, Boolean unique) {
        if (unique) {
            Map<String, Integer> uniqueHits = uris.stream().collect(Collectors.toMap(uri -> uri, statsRepository::countDistinctIp, (a, b) -> b));
            return findStatsByHits(start, end, uris, uniqueHits);
        } else {
            Map<String, Integer> hits = uris.stream().collect(Collectors.toMap(uri -> uri, statsRepository::countIp, (a, b) -> b));
            return findStatsByHits(start, end, uris, hits);
        }
    }

    @Override
    @Transactional
    public EndpointHit save(EndpointHit endpointHit) {
        final Stats stats = StatsMapper.toStats(endpointHit);
        final Stats statsToSave = statsRepository.save(stats);
        return StatsMapper.toEndpointHit(statsToSave);
    }

    private List<ViewStats> findStatsByHits(String start, String end, List<String> uris, Map<String, Integer> hits) {
        /*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return statsRepository.findAllByUriInAndTimestampBetween(uris, LocalDateTime.parse(start, formatter), LocalDateTime.parse(end, formatter))
                .stream()
                .filter(distinctByKey(Stats::getUri))
                .map(it -> StatsMapper.toViewStats(it, hits.get(it.getUri())))
                .sorted(Comparator.comparing(ViewStats::getHits).reversed())
                .collect(Collectors.toList());*/

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return statsRepository
                .findAllByUriInAndTimestampBetween(uris, LocalDateTime.parse(start, formatter), LocalDateTime.parse(end, formatter))
                .stream()
                .collect(Collectors.groupingBy(Stats::getUri))
                .values()
                .stream()
                .map(statsList -> statsList.get(0))
                .map(stats -> StatsMapper.toViewStats(stats, hits.get(stats.getUri())))
                .sorted(Comparator.comparing(ViewStats::getHits).reversed())
                .collect(Collectors.toList());
    }
}
