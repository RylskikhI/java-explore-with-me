package ru.practicum.dto.comment;

import lombok.Builder;
import lombok.Data;
import ru.practicum.enums.CommentState;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDtoResponse {
    private Long commentId;
    private String text;
    private CommentState state;
    private LocalDateTime created;
    private String authorName;
    private String eventTitle;
}
