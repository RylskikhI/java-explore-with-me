package ru.practicum.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.dto.category.ListCategoryDto;
import ru.practicum.dto.category.NewCategoryDto;
import ru.practicum.dto.category.NewCategoryDtoResp;
import ru.practicum.dto.category.UpdateCategoryDto;

public interface CategoryService {
    NewCategoryDtoResp createCategory(NewCategoryDto newCategoryDto);

    NewCategoryDtoResp updateCategory(UpdateCategoryDto updateCategory, Long catId);

    void deleteCategory(Long catId);

    ListCategoryDto getCategories(Pageable pageable);

    NewCategoryDtoResp getCategoryById(Long catId);
}
