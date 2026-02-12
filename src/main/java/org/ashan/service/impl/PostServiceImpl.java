package org.ashan.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.ashan.domain.dto.post.CommentDto;
import org.ashan.domain.dto.post.CreatePost;
import org.ashan.domain.dto.post.GetAllPosts;
import org.ashan.domain.dto.post.PostDto;
import org.ashan.domain.entity.Comment;
import org.ashan.domain.entity.Post;
import org.ashan.domain.entity.User;
import org.ashan.domain.enums.PostStatus;
import org.ashan.repository.PostRepository;
import org.ashan.repository.UserRepository;
import org.ashan.service.PostService;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public void createPost(CreatePost request) {

        String title = request.getTitle();
        String description = request.getDescription();
        Long userId = request.getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Invalid author details"));


        Post post = new Post();
        post.setTitle(title);
        post.setDescription(description);
        post.setUser(user);
        post.setStatus(PostStatus.VISIBLE);

        postRepository.save(post);

    }

    public List<GetAllPosts> getAllPosts(){

        List<Post> posts = postRepository.findAllActivePosts();
        return posts.stream()
                .map(this::mapToPostDto)
                .toList();
    }

    private GetAllPosts mapToPostDto(Post post){

        GetAllPosts allPosts = new GetAllPosts();
        allPosts.setId(post.getId());
        allPosts.setTitle(post.getTitle());
        allPosts.setDescription(post.getDescription());
        allPosts.setAuthor(post.getUser().getName());

        return allPosts;
    }

    @Override
    public PostDto showPost(Long id) {

        Post post = postRepository.getPostById(id).orElseThrow(
                () ->new EntityNotFoundException("Invalid post id provided"));

        PostDto pDto = new PostDto();
        pDto.setTitle(post.getTitle());
        pDto.setDescription(post.getDescription());
        pDto.setAuthor(post.getUser().getName());

        List<CommentDto> comments = post.getComments().stream().map(this::mapCommentsDto).toList();
        pDto.setComments(comments);

        return pDto;

    }

    private CommentDto mapCommentsDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentId(comment.getId());
        commentDto.setComment(comment.getComment());
        commentDto.setCommentor(comment.getUser().getName());

        return commentDto;
    }
}
