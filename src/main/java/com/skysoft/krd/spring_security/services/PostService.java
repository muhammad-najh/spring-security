package com.skysoft.krd.spring_security.services;


import com.skysoft.krd.spring_security.dto.PostDTO;

import java.util.List;

public interface PostService {

    List<PostDTO> getAllPosts();

    PostDTO createNewPost(PostDTO inputPost);

    PostDTO getPostById(Long postId);
}
