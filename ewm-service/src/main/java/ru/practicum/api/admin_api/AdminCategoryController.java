package ru.practicum.api.admin_api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.category.NewCategoryDto;
import ru.practicum.dto.category.NewCategoryDtoResp;
import ru.practicum.dto.category.UpdateCategoryDto;
import ru.practicum.service.CategoryService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/admin/categories")
@Slf4j
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminCategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<NewCategoryDtoResp> createCategory(@RequestBody @Valid NewCategoryDto category) {
        log.info("create category:{}", category);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(category));
    }

    @PatchMapping("{catId}")
    public ResponseEntity<NewCategoryDtoResp> updateCategory(@PathVariable @Min(1) Long catId,
                                                             @RequestBody @Valid UpdateCategoryDto updateCategory) {
        log.info("update category:{} with id={}",updateCategory, catId);
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.updateCategory(updateCategory, catId));
    }

    @DeleteMapping("{catId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable @Min(1) Long catId) {
        log.info("delete category with id={}", catId);
        categoryService.deleteCategory(catId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}