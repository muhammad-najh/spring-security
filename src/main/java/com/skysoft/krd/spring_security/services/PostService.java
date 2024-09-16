package com.skysoft.krd.spring_security.services;


import com.skysoft.krd.spring_security.dto.PostDto;

import java.util.List;

public interface PostService {

    List<PostDto> getAllPosts();

    PostDto createNewPost(PostDto inputPost);

    PostDto getPostById(Long postId);
}
