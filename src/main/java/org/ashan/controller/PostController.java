package org.ashan.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ashan.domain.dto.ApiJsonResponse;
import org.ashan.domain.dto.post.CreatePost;
import org.ashan.domain.dto.post.GetAllPosts;
import org.ashan.domain.dto.post.PostDto;
import org.ashan.domain.entity.Post;
import org.ashan.service.PostService;
import org.ashan.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<ApiJsonResponse<Void>> createPost(@Valid @RequestBody CreatePost request){

        postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiJsonResponse<>(true,"Post created successfully",null));

    }

    @GetMapping("all-posts")
    public ResponseEntity<ApiJsonResponse<List<GetAllPosts>>> getAllPosts(){
        List<GetAllPosts> posts = postService.getAllPosts();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiJsonResponse<>(true,"Posts retrieved",posts));
    }

    @GetMapping("show/{id}")
    public ResponseEntity<ApiJsonResponse<PostDto>>showPost(@PathVariable Long id){
        PostDto dto = postService.showPost(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiJsonResponse<>(true,"Post retrieved successfully",dto));
    }

}
