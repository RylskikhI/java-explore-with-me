package ru.practicum.dto.event;

import lombok.Builder;
import lombok.Data;
import ru.practicum.dto.category.NewCategoryDtoResp;
import ru.practicum.dto.comment.CommentDtoShort;
import ru.practicum.dto.location.Location;
import ru.practicum.dto.user.UserShortDto;
import ru.practicum.enums.State;

import java.util.Set;

@Data
@Builder
public class EventFullDto {
    private Long id;
    private String annotation;
    private NewCategoryDtoResp category;
    private Integer confirmedRequests;
    private String createdOn;
    private String description;
    private String eventDate;
    private Location location;
    private UserShortDto initiator;
    private Boolean paid;
    private Integer participantLimit;
    private String publishedOn;
    private Boolean requestModeration;
    private State state;
    private String title;
    private Long views;
    private Set<CommentDtoShort> comments;
}
