package ru.practicum.mapper;

import org.mapstruct.*;
import ru.practicum.dto.event.*;
import ru.practicum.enums.State;
import ru.practicum.model.Event;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, UserMapper.class, CommentMapper.class})
public interface EventMapper {
    @Mapping(target = "category", ignore = true)
    @Mapping(source = "eventDate", target = "eventDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    Event mapToEvent(NewEventDto eventDto);

    @Mapping(source = "eventId", target = "id")
    @Mapping(source = "eventDate", target = "eventDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(source = "initiator.userId", target = "initiator.id")
    @Mapping(source = "createdOn", target = "createdOn", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(source = "publishedOn", target = "publishedOn", dateFormat = "yyyy-MM-dd HH:mm:ss")
    EventFullDto mapToEventFullDto(Event event);

    @Mapping(source = "eventDate", target = "eventDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(source = "initiator.userId", target = "initiator.id")
    @Mapping(source = "eventId", target = "id")
    EventShortDto mapToEventShortDto(Event event);

    List<EventShortDto> mapToListEventShortDto(List<Event> events);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category", ignore = true)
    @Mapping(source = "eventDate", target = "eventDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    Event mapToEvent(UpdateEventUserRequest updateEvent, @MappingTarget Event event);

    List<EventFullDto> mapToListEventFullDto(List<Event> events);

    State mapToState(String state);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category", ignore = true)
    @Mapping(source = "eventDate", target = "eventDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    Event mapToEvent(UpdateEventAdminRequest updateEvent, @MappingTarget Event event);
}
