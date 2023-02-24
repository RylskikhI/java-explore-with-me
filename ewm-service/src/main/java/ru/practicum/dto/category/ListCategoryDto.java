package ru.practicum.dto.category;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ListCategoryDto {
    @JsonValue
    private List<NewCategoryDtoResp> catList;
}
