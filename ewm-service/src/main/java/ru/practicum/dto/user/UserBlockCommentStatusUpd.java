package ru.practicum.dto.user;

import lombok.Builder;
import lombok.Data;
import ru.practicum.enums.UserBanAction;

import java.util.List;

@Data
@Builder
public class UserBlockCommentStatusUpd {
    private List<Long> userIds;
    private UserBanAction status;
}
