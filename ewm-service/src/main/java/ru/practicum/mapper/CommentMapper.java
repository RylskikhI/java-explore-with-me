package ru.practicum.mapper;

import org.mapstruct.*;
import ru.practicum.dto.comment.CommentDtoResponse;
import ru.practicum.dto.comment.CommentDtoShort;
import ru.practicum.dto.comment.CommentDtoUpdate;
import ru.practicum.dto.comment.NewCommentDto;
import ru.practicum.model.Comment;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment mapToComment(NewCommentDto newComment);

    @Mapping(source = "author.name", target = "authorName")
    @Mapping(source = "event.title", target = "eventTitle")
    @Mapping(source = "created", target = "created", dateFormat = "yyyy-MM-dd HH:mm:ss")
    CommentDtoResponse mapToCommentResponse(Comment comment);

    @Mapping(source = "author.name", target = "authorName")
    @Mapping(source = "created", target = "created", dateFormat = "yyyy-MM-dd HH:mm:ss")
    CommentDtoShort mapToCommentShortDto(Comment comment);

    Set<CommentDtoShort> mapToSetCommentShort(Set<Comment> comments);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Comment mapToComment(CommentDtoUpdate updateComment, @MappingTarget Comment comment);
}
