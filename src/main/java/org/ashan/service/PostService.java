package org.ashan.service;

import org.ashan.domain.dto.post.CreatePost;
import org.ashan.domain.dto.post.GetAllPosts;
import org.ashan.domain.dto.post.PostDto;
import org.ashan.domain.entity.Post;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PostService {

    void createPost(CreatePost request);

    List<GetAllPosts> getAllPosts();

    PostDto showPost(Long id);
}
