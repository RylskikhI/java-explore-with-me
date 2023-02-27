package ru.practicum.dto.event;

import lombok.Builder;
import lombok.Data;
import ru.practicum.dto.category.NewCategoryDtoResp;
import ru.practicum.dto.user.UserShortDto;

@Data
@Builder
public class EventShortDto {
    private Long id;
    private String annotation;
    private NewCategoryDtoResp category;
    private Integer confirmedRequests;
    private String eventDate;
    private UserShortDto initiator;
    private Boolean paid;
    private String title;
    private Long views;
}
