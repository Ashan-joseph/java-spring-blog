package org.ashan.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ashan.domain.dto.comment.ChangeVisibility;
import org.ashan.domain.dto.comment.CreateCommentRequest;
import org.ashan.domain.dto.comment.UserComment;
import org.ashan.domain.dto.post.CommentDto;
import org.ashan.domain.entity.Comment;
import org.ashan.domain.entity.Post;
import org.ashan.domain.entity.User;
import org.ashan.domain.enums.CommentStatus;
import org.ashan.repository.CommentRepository;
import org.ashan.repository.PostRepository;
import org.ashan.repository.UserRepository;
import org.ashan.service.CommentService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public void createComment(CreateCommentRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setComment(request.getComment());
        comment.setIsVisible(CommentStatus.VISIBLE);
        commentRepository.save(comment);

    }

    @Override
    public void changeCommentVisibility(ChangeVisibility changeVisibility) {
        Long commentId = changeVisibility.getCommentId();
        CommentStatus status = changeVisibility.getCommentStatus();

        User user = userRepository.findById(changeVisibility.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));

        int updated = commentRepository.updateCommentStatus(commentId,status,user);

        if(updated == 0){
            throw new RuntimeException("An error occurred. status could not changed");
        }


    }

    @Override
    public List<UserComment> getUserComments(Long id) {

        userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));

        List<Comment> comments = commentRepository.getUserRelatedComments(id);

        return comments.stream().map(this::mapUseCommentDto).toList();
    }

    private UserComment mapUseCommentDto(Comment comment){

        UserComment userComment = new UserComment();
        userComment.setComment(comment.getComment());
        userComment.setPostName(comment.getPost().getTitle());

        return userComment;

    }
}
