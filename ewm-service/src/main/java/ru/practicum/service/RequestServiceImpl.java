package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.request.ParticipationRequestDto;
import ru.practicum.dto.request.ParticipationRequestList;
import ru.practicum.enums.RequestStatus;
import ru.practicum.enums.State;
import ru.practicum.exception.AccessException;
import ru.practicum.mapper.RequestMapper;
import ru.practicum.model.Event;
import ru.practicum.model.Request;
import ru.practicum.model.User;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.RequestRepository;
import ru.practicum.repository.UserRepository;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requests;
    private final UserRepository users;
    private final EventRepository events;
    private final RequestMapper mapper;

    @Override
    @Transactional
    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
        Event event = events.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event with id=" + eventId + " was not found"));
        User requester = users.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id=" + userId + " was not found"));
        validateCreatingRequest(event, userId);
        if (event.getParticipantLimit() == 0 || event.getParticipantLimit() > event.getConfirmedRequests()) {
            var newRequest = preparationRequest(event, requester);
            if (!event.getRequestModeration()) {
                newRequest.setStatus(RequestStatus.CONFIRMED);
                event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                events.save(event);
            } else {
                newRequest.setStatus(RequestStatus.PENDING);
            }
            return mapper.mapToRequestDto(requests.save(newRequest));
        } else {
            throw new AccessException("Request limit reached");
        }
    }

    @Override
    public ParticipationRequestList getUserRequests(Long userId) {
        if (users.existsById(userId)) {
            return ParticipationRequestList
                    .builder()
                    .requests(mapper.mapToRequestDtoList(requests.findAllByRequesterUserId(userId)))
                    .build();
        } else {
            throw new EntityNotFoundException("User with id=" + userId + " was not found");
        }
    }

    @Override
    @Transactional
    public ParticipationRequestDto canceledRequest(Long userId, Long requestId) {
        if (!users.existsById(userId)) {
            throw new EntityNotFoundException("User with id=" + userId + " was not found");
        }
        Request request = requests.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request with id=" + requestId + " was not found"));
        Event event = request.getEvent();
        if (request.getStatus().equals(RequestStatus.CONFIRMED)) {
            event.setConfirmedRequests(event.getConfirmedRequests() - 1);
            request.setStatus(RequestStatus.CANCELED);
            events.save(event);
        } else {
            request.setStatus(RequestStatus.CANCELED);
        }
        return mapper.mapToRequestDto(requests.save(request));
    }

    private Request preparationRequest(Event event, User requester) {
        Request newRequest = new Request();
        newRequest.setEvent(event);
        newRequest.setRequester(requester);
        return newRequest;
    }

    private void validateCreatingRequest(Event event, Long userId) {
        if (event.getInitiator().getUserId().equals(userId)) {
            throw new AccessException("You can't create a request to participate in your own event");
        }
        if (event.getState() != State.PUBLISHED) {
            throw new AccessException("You cannot participate in an unpublished event");
        }
    }
}
