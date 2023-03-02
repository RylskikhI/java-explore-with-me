package ru.practicum.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NewUserRequestResponse {
    private Long id;
    private String name;
    private String email;
    private Boolean areCommentsBlocked;
}
