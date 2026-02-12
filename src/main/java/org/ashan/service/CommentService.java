package org.ashan.service;


import org.ashan.domain.dto.comment.ChangeVisibility;
import org.ashan.domain.dto.comment.CreateCommentRequest;
import org.ashan.domain.dto.comment.UserComment;
import org.ashan.domain.entity.Comment;

import java.util.List;

public interface CommentService {

    void createComment(CreateCommentRequest request);

    void changeCommentVisibility(ChangeVisibility changeVisibility);

    List<UserComment> getUserComments(Long id);
}
