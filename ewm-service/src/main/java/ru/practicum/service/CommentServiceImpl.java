package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.comment.CommentDtoList;
import ru.practicum.dto.comment.CommentDtoResponse;
import ru.practicum.dto.comment.CommentDtoUpdate;
import ru.practicum.dto.comment.NewCommentDto;
import ru.practicum.enums.CommentState;
import ru.practicum.enums.State;
import ru.practicum.exception.AccessException;
import ru.practicum.exception.CommentException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.CommentMapper;
import ru.practicum.model.Comment;
import ru.practicum.model.Event;
import ru.practicum.model.Report;
import ru.practicum.model.User;
import ru.practicum.repository.CommentRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.ReportRepository;
import ru.practicum.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper mapper;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final ReportRepository reportRepository;

    @Override
    public CommentDtoResponse addComment(Long userId, Long eventId, NewCommentDto newComment) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id=" + userId + " was not found"));
        Event event;
        if (user.getAreCommentsBlocked()) {
            throw new AccessException("A user with id=" + userId + " has comments blocked");
        }
        event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " was not found"));
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new AccessException("It is impossible to add a comment to an event in the status " + event.getState());
        }
        Comment comment = mapper.mapToComment(newComment);
        comment.setAuthor(user);
        comment.setEvent(event);
        comment.setCreated(LocalDateTime.now());
        return mapper.mapToCommentResponse(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public CommentDtoResponse editCommentByUser(Long userId, Long commentId, CommentDtoUpdate updateComment) {
        Comment comment = commentRepository.findByCommentIdAndAuthorUserId(commentId, userId)
                .orElseThrow(() -> new NotFoundException("Comment with commentId=" + commentId
                        + " and userId=" + userId + " not found"));
        if (LocalDateTime.now().isAfter(comment.getCreated().plusHours(2))) {
            throw new CommentException("It is impossible to edit a comment that was created more than 2 hours ago");
        }
        comment.setState(CommentState.EDITED);
        return mapper.mapToCommentResponse(commentRepository.save(mapper.mapToComment(updateComment, comment)));
    }

    @Override
    public void removeCommentByUser(Long commentId, Long userId) {
        Comment comment = commentRepository.findByCommentIdAndAuthorUserId(commentId, userId)
                .orElseThrow(() -> new NotFoundException("Comment with commentId=" + commentId
                        + " and userId=" + userId + " not found"));
        if (LocalDateTime.now().isAfter(comment.getCreated().plusHours(2))) {
            throw new CommentException("СIt is impossible to remove a comment that was created more than 2 hours ago");
        }
        commentRepository.deleteById(commentId);
    }

    @Override
    public void reportComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findByCommentIdAndAuthorUserId(commentId, userId)
                .orElseThrow(() -> new NotFoundException("Comment with commentId=" + commentId
                        + " and userId=" + userId + " not found"));
        Report report = new Report();
        report.setReportedUser(comment.getAuthor());
        report.setReportedMessage(comment.getText());
        reportRepository.save(report);
    }

    @Override
    public void removeCommentByAdmin(Long commentId, Long userId) {
        if (commentRepository.existsByCommentIdAndAuthorUserId(commentId, userId)) {
            commentRepository.deleteById(commentId);
        } else {
            throw new NotFoundException("Comment with commentId=" + commentId
                    + " and userId=" + userId + " not found");
        }
    }

    @Override
    public CommentDtoResponse editCommentByAdmin(Long userId, Long commentId, CommentDtoUpdate updateComment) {
        Comment comment = commentRepository.findByCommentIdAndAuthorUserId(commentId, userId)
                .orElseThrow(() -> new NotFoundException("Comment with commentId=" + commentId
                        + " and userId=" + userId + " not found"));
        comment.setState(CommentState.EDITED);
        return mapper.mapToCommentResponse(commentRepository.save(mapper.mapToComment(updateComment, comment)));
    }

    @Override
    public CommentDtoResponse viewComment(Long userId, Long commentId) {
        Comment comment = commentRepository.findByCommentIdAndAuthorUserId(commentId, userId)
                .orElseThrow(() -> new NotFoundException("Comment with commentId=" + commentId
                        + " and userId=" + userId + " not found"));
        return mapper.mapToCommentResponse(comment);
    }

    @Override
    public CommentDtoList listComments(Long userId, Long eventId) {
        return CommentDtoList
                .builder()
                .comments(commentRepository.findAllByAuthorUserIdAndEventEventId(userId, eventId).stream()
                        .map(mapper::mapToCommentShortDto).collect(Collectors.toList()))
                .build();
    }

    @Override
    public CommentDtoList listCommentsByEvent(Long eventId) {
        return CommentDtoList
                .builder()
                .comments(commentRepository.findAllByEventEventId(eventId).stream()
                        .map(mapper::mapToCommentShortDto).collect(Collectors.toList()))
                .build();
    }

    @Override
    public CommentDtoResponse viewCommentById(Long commentId) {
        return mapper.mapToCommentResponse(commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment with commentId=" + commentId + " not found")));
    }
}
