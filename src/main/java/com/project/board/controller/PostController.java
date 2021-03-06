package com.project.board.controller;

import com.project.board.domain.post.dto.*;
import com.project.board.domain.post.web.PostService;
import com.project.board.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public Long write(@AuthenticationPrincipal UserDetailsImpl userDetails,
                      @RequestParam("category") Long postCategoryId,
                      @RequestBody PostWriteRequestDto postWriteRequestDto) {
        return postService.write(userDetails.getUsername(), postCategoryId, postWriteRequestDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated() " +
            "and ((#PUReqDto.writer == principal.username) " +
            "or hasRole('ROLE_ADMIN'))")
    public Long update(@PathVariable Long id,
                       @RequestBody PostUpdateRequestDto PUReqDto) {
        return postService.update(id, PUReqDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated() " +
            "and ((#PDReqDto.writer == principal.username) " +
            "or hasRole('ROLE_ADMIN'))")
    public Long delete(@PathVariable Long id,
                       @RequestBody PostDeleteRequestDto PDReqDto) {
        return postService.delete(id);
    }

    @GetMapping
    public Page<PostsResponseDto> getPostsInPostCategory(
            @RequestParam("category") Long postCategoryId,
            @RequestParam Integer page) {
        return postService.getPostsInPostCategory(postCategoryId, page);
    }

    @GetMapping("/search")
    public Page<PostsResponseDto> getPostsBySearchKeywordInBoard(
            @RequestParam("board") Long boardId,
            @RequestParam Integer page,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String keyword) {
        // type??? null??? ????????? ?????? ??? ????????? ??????
        if (StringUtils.isEmpty(type)) {
            type = "";
        }

        if (type.equals(PostSearchType.TICON.stringValue)) {
            return postService.getPostsByPostTitleOrPostContentInBoard(boardId, keyword, page);
        }
        if (type.equals(PostSearchType.TITLE.stringValue)) {
            return postService.getPostsByPostTitleInBoard(boardId, keyword, page);
        }
        if (type.equals(PostSearchType.CONTENT.stringValue)) {
            return postService.getPostsByPostContentInBoard(boardId, keyword, page);
        }
        if (type.equals(PostSearchType.NICKNAME.stringValue)) {
            return postService.getPostsByUserNicknameInBoard(boardId, keyword, page);
        }

        return postService.getPostsInBoard(boardId, page);
    }

    @GetMapping("/{id}")
    public PostDetailsResponseDto getPostDetails(@PathVariable Long id) {
        return postService.getPostDetails(id);
    }
}
