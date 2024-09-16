package com.skysoft.krd.spring_security.controllers;

import com.skysoft.krd.spring_security.dto.PostDto;
import com.skysoft.krd.spring_security.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/posts")
@RequiredArgsConstructor

public class PostController {

    private final PostService postService;

    @GetMapping
    @Secured({"ROLE_USER","ROLE_ADMIN"}) // used to define role-based access control at the method level.
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN') AND hasAuthority('POST_VIEW')") // used to secure methods based on expressions
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{postId}")
    @PreAuthorize("@postSecurityService.isOwnerOfPost(#postId)") //calls specific method of a bean
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @PostMapping
    public ResponseEntity<PostDto> createNewPost(@RequestBody PostDto inputPost) {
        return new ResponseEntity<>(postService.createNewPost(inputPost), HttpStatus.CREATED);
    }

}
