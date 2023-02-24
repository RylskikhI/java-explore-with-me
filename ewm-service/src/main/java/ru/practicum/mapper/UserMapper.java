package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import ru.practicum.dto.user.NewUserRequest;
import ru.practicum.dto.user.NewUserRequestResponse;
import ru.practicum.dto.user.UserShortDto;
import ru.practicum.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User mapToUser(NewUserRequest userRequest);

    @Mapping(source = "userId", target = "id")
    NewUserRequestResponse mapToUserRequestResp(User user);

    List<NewUserRequestResponse> mapToUserRequestResp(Page<User> page);

    UserShortDto mapToUserShortDto(User user);
}
