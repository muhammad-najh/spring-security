package com.skysoft.krd.spring_security.utils;

import com.skysoft.krd.spring_security.dto.PostDto;
import com.skysoft.krd.spring_security.entities.User;
import com.skysoft.krd.spring_security.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSecurityService {
    private final PostService postService;

    public boolean isOwnerOfPost(Long postId){
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostDto post=postService.getPostById(postId);

        return post.getAuthor().getId().equals(user.getId());

    }
}
