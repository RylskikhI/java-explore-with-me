package ru.practicum.dto.category;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@Jacksonized
public class NewCategoryDto {
    @Pattern(regexp = "^[^ ].*[^ ]$", message = "Invalid name")
    @Size(max = 255)
    @NotNull(message = "Field: name. Error: must not be blank. Value: null")
    private String name;
}
