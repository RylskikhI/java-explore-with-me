package ru.practicum.dto.event;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ListEventFullDto {
    @JsonValue
    private List<EventFullDto> events;
}
