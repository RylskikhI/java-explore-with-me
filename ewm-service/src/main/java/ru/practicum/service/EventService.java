package ru.practicum.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.dto.event.*;
import ru.practicum.dto.request.EventRequestStatusUpdate;
import ru.practicum.dto.request.EventRequestStatusUpdateResult;
import ru.practicum.dto.request.ParticipationRequestList;


import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    EventFullDto addNewEvent(Long userId, NewEventDto eventDto);

    ListEventShortDto getPrivateUserEvents(Long userId, Pageable pageable);

    EventFullDto getPrivateUserEvent(Long userId, Long eventId);

    EventFullDto updateEventUser(Long userId, Long eventId, UpdateEventUserRequest updateEvent);

    ListEventFullDto getEventsByFiltersForAdmin(List<Long> ids, List<String> states, List<Long> categories,
                                                LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);

    EventFullDto updateEventAdmin(Long eventId, UpdateEventAdminRequest updateEvent);

    ParticipationRequestList getUserEventRequests(Long userId, Long eventId);

    EventRequestStatusUpdateResult approveRequests(Long userId, Long eventId, EventRequestStatusUpdate requests);

    EventFullDto getEventByIdPublic(Long eventId, HttpServletRequest servlet);

    ListEventShortDto getEventsByFiltersPublic(String text, List<Long> categories, Boolean paid,
                                               LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable,
                                               Pageable pageable, HttpServletRequest servlet);
}
