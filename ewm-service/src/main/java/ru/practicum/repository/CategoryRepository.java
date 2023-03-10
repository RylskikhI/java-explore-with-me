package ru.practicum.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Category;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {
}
