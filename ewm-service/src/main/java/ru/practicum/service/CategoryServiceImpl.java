package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.dto.category.ListCategoryDto;
import ru.practicum.dto.category.NewCategoryDto;
import ru.practicum.dto.category.NewCategoryDtoResp;
import ru.practicum.dto.category.UpdateCategoryDto;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.CategoryMapper;
import ru.practicum.repository.CategoryRepository;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categories;
    private final CategoryMapper mapper;

    @Override
    public NewCategoryDtoResp createCategory(NewCategoryDto newCategoryDto) {
        return mapper.mapToNewCategoryDtoResp(categories.save(mapper.mapToCategory(newCategoryDto)));
    }

    @Override
    public NewCategoryDtoResp updateCategory(UpdateCategoryDto updateCategory, Long catId) {
        if (categories.existsById(catId)) {
            return mapper.mapToNewCategoryDtoResp(categories.save(mapper.mapToCategory(updateCategory)));
        } else {
            throw new NotFoundException("Category with id=" + catId + " was not found");
        }
    }

    @Override
    public void deleteCategory(Long catId) {
        if (!categories.existsById(catId)) {
            throw new NotFoundException("Category with id=" + catId + " was not found");
        } else {
            categories.deleteById(catId);
        }
    }

    @Override
    public ListCategoryDto getCategories(Pageable pageable) {
        return ListCategoryDto
                .builder()
                .catList(mapper.mapToListCategories(categories.findAll(pageable)))
                .build();
    }

    @Override
    public NewCategoryDtoResp getCategoryById(Long catId) {
        return mapper.mapToNewCategoryDtoResp(categories.findById(catId)
                .orElseThrow(() -> new NotFoundException("Category with id=" + catId + " was not found")));
    }
}
