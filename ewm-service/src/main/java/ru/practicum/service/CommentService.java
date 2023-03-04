package ru.practicum.service;

import ru.practicum.dto.comment.CommentDtoList;
import ru.practicum.dto.comment.CommentDtoResponse;
import ru.practicum.dto.comment.CommentDtoUpdate;
import ru.practicum.dto.comment.NewCommentDto;

public interface CommentService {
    CommentDtoResponse addComment(Long userId, Long eventId, NewCommentDto newComment);

    CommentDtoResponse editCommentByUser(Long userId, Long commentId, CommentDtoUpdate updateComment);

    void removeCommentByUser(Long commentId, Long userId);

    void reportComment(Long commentId, Long userId);

    void removeCommentByAdmin(Long commentId, Long userId);

    CommentDtoResponse editCommentByAdmin(Long userId, Long commentId, CommentDtoUpdate updateComment);

    CommentDtoResponse viewComment(Long userId, Long commentId);

    CommentDtoList listComments(Long userId, Long eventId);

    CommentDtoList listCommentsByEvent(Long eventId);

    CommentDtoResponse viewCommentById(Long commentId);
}
