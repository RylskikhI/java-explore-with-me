package ru.practicum.dto.request;

import lombok.Builder;
import lombok.Data;
import ru.practicum.enums.RequestStatus;

@Data
@Builder
public class ParticipationRequestDto {
    private Long id;
    private Long event;
    private Long requester;
    private RequestStatus status;
    private String created;
}
