package com.project.board.domain.controller;

import com.project.board.domain.like.dto.LikePostRequestDto;
import com.project.board.domain.service.LikePostService;
import com.project.board.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikePostController {

    private final LikePostService likePostService;

    @PostMapping("/api/post/{postId}/like-post")
    public Long open(@AuthenticationPrincipal UserDetailsImpl userDetails,
                     @PathVariable Long postId,
                     @RequestBody LikePostRequestDto likePostRequestDto) {
        return likePostService.like(userDetails.getUsername(), postId, likePostRequestDto);
    }

    @DeleteMapping("/api/like-post/{likePostId}")
    public Long cancel(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       @PathVariable Long likePostId) {
        return likePostService.cancel(userDetails.getUsername(), likePostId);
    }
}
