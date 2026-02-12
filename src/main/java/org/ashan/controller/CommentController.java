package org.ashan.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ashan.domain.dto.ApiJsonResponse;
import org.ashan.domain.dto.comment.ChangeVisibility;
import org.ashan.domain.dto.comment.CreateCommentRequest;
import org.ashan.domain.dto.comment.UserComment;
import org.ashan.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiJsonResponse<Void>> saveComment(@Valid @RequestBody CreateCommentRequest request){

        commentService.createComment(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiJsonResponse<>(true,"Comment saved successfully",null));
    }

    @PostMapping("/change-visibility")
    public ResponseEntity<ApiJsonResponse<Void>> changeCommentVisibility(@Valid @RequestBody ChangeVisibility changeVisibility){

        commentService.changeCommentVisibility(changeVisibility);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiJsonResponse<>(true,"Comment status updated successfully",null));

    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ApiJsonResponse<List<UserComment>>> getUserComments(@PathVariable Long id){

        List<UserComment> userComment = commentService.getUserComments(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiJsonResponse<>(true, "User comments retrieved successfully",userComment));

    }


}
