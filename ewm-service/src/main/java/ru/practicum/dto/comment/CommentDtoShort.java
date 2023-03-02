package ru.practicum.dto.comment;

import lombok.Builder;
import lombok.Data;
import ru.practicum.enums.CommentState;

@Data
@Builder
public class CommentDtoShort {
    private String text;
    private String authorName;
    private CommentState state;
    private String created;
}
