package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import ru.practicum.dto.category.NewCategoryDto;
import ru.practicum.dto.category.NewCategoryDtoResp;
import ru.practicum.dto.category.UpdateCategoryDto;
import ru.practicum.model.Category;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category mapToCategory(NewCategoryDto newCategoryDto);

    @Mapping(source = "categoryId", target = "id")
    NewCategoryDtoResp mapToNewCategoryDtoResp(Category category);

    Category mapToCategory(UpdateCategoryDto updateCategory);

    List<NewCategoryDtoResp> mapToListCategories(Page<Category> page);
}