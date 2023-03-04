package ru.practicum.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.dto.user.ListNewUserRequestResp;
import ru.practicum.dto.user.NewUserRequest;
import ru.practicum.dto.user.NewUserRequestResponse;
import ru.practicum.dto.user.UserBlockCommentStatusUpd;

import java.util.List;

public interface UserService {
    NewUserRequestResponse createUser(NewUserRequest userRequest);

    ListNewUserRequestResp getUsers(List<Long> ids, Pageable pageable);

    void deleteUser(Long userId);

    ListNewUserRequestResp updateUserCommentsStatus(UserBlockCommentStatusUpd users);
}
