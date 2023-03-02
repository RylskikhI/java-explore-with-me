package ru.practicum.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.model.User;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>, QuerydslPredicateExecutor<User> {
    List<User> findAllByUserIdIn(List<Long> ids);
}
