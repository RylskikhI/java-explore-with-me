package ru.practicum.dto.comment;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CommentDtoList {
    @JsonValue
    private List<CommentDtoShort> comments;
}
