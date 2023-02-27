package ru.practicum.model;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.dto.location.Location;
import ru.practicum.enums.State;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;
    @Column(nullable = false, length = 2000)
    private String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(nullable = false, length = 7000)
    private String description;
    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;
    @Embedded
    private Location location;
    @Column
    private Boolean paid = Boolean.FALSE;
    @Column(name = "participant_limit")
    private Integer participantLimit = 0;
    @Column(name = "request_moderation")
    private Boolean requestModeration = Boolean.TRUE;
    @Column(nullable = false, length = 120)
    private String title;
    @Column(name = "confirmed_requests")
    private Integer confirmedRequests = 0;
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;
    @ManyToOne
    @JoinColumn(name = "initiator")
    private User initiator;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    @Enumerated(EnumType.STRING)
    private State state = State.PENDING;
    @Column
    private Long views = 0L;
    @OneToMany(mappedBy = "event")
    private Set<Request> requests;
    @ManyToMany(mappedBy = "events")
    private Set<Compilation> compilations;
}
