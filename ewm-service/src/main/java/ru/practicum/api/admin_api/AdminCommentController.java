package ru.practicum.api.admin_api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.comment.CommentDtoResponse;
import ru.practicum.dto.comment.CommentDtoUpdate;
import ru.practicum.service.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/admin/users")
@Validated
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminCommentController {
    private final CommentService commentService;

    @DeleteMapping("{userId}/comments/{commentId}")
    public ResponseEntity<Void> removeCommentByAdmin(@PathVariable @Min(1) Long userId,
                                                   @PathVariable @Min(1) Long commentId) {
        log.info("admin delete comment with userId={} and commentId={}", userId, commentId);
        commentService.removeCommentByAdmin(commentId, userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("{userId}/comments/{commentId}")
    public ResponseEntity<CommentDtoResponse> editCommentByAdmin(@PathVariable @Min(1) Long userId,
                                                                 @PathVariable @Min(1) Long commentId,
                                                                 @RequestBody @Valid CommentDtoUpdate updateComment) {
        log.info("update comment with commentId={} and userId={}: {}", commentId, userId, updateComment);
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.editCommentByAdmin(userId, commentId, updateComment));
    }
}
