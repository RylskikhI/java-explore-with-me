package ru.practicum.service;

import ru.practicum.dto.request.ParticipationRequestDto;
import ru.practicum.dto.request.ParticipationRequestList;

public interface RequestService {
    ParticipationRequestDto createRequest(Long userId, Long eventId);

    ParticipationRequestList getUserRequests(Long userId);

    ParticipationRequestDto canceledRequest(Long userId, Long eventId);
}
