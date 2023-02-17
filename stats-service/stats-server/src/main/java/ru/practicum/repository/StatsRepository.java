package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.model.Stats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<Stats, Long> {

    List<Stats> findAllByUriInAndTimestampBetween(List<String> uris, LocalDateTime start, LocalDateTime end);

    @Query("select count(s.ip) from Stats as s where s.uri = ?1")
    Integer countIp(String uri);

    @Query("select count(distinct s.ip) from Stats as s where s.uri = ?1")
    Integer countDistinctIp(String uri);
}
