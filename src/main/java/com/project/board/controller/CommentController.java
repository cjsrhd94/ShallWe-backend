package com.project.board.controller;

import com.project.board.domain.comment.dto.CommentDeleteRequestDto;
import com.project.board.domain.comment.dto.CommentUpdateRequestDto;
import com.project.board.domain.comment.dto.CommentWriteRequestDto;
import com.project.board.domain.comment.dto.ParentCommentsResponseDto;
import com.project.board.domain.comment.web.CommentService;
import com.project.board.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/post/{postId}/comment")
    public Long writeParentComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                   @PathVariable Long postId,
                                   @RequestBody CommentWriteRequestDto commentWriteRequestDto) {
        return commentService.writeParentComment(userDetails.getUsername(), postId, commentWriteRequestDto);
    }

    @PostMapping("/api/post/{postId}/comment/{commentId}")
    public Long writeChildComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                  @PathVariable Long postId,
                                  @PathVariable Long commentId,
                                  @RequestBody CommentWriteRequestDto commentWriteRequestDto) {
        return commentService.writeChildComment(userDetails.getUsername(), postId, commentId, commentWriteRequestDto);
    }

    @PutMapping("/api/comment/{commentId}")
    @PreAuthorize("isAuthenticated() " +
            "and ((#CUReqDto.writer == principal.username) " +
            "or hasRole('ROLE_ADMIN'))")
    public Long update(@PathVariable Long commentId,
                       @RequestBody CommentUpdateRequestDto CUReqDto) {
        return commentService.update(commentId, CUReqDto);
    }

    @DeleteMapping("/api/comment/{commentId}")
    @PreAuthorize("isAuthenticated() " +
            "and ((#CDReqDto.writer == principal.username) " +
            "or hasRole('ROLE_ADMIN'))")
    public Long delete(@PathVariable Long commentId,
                       @RequestBody CommentDeleteRequestDto CDReqDto){
        return commentService.delete(commentId);
    }

    @GetMapping("/api/post/{postId}/comment")
    public List<ParentCommentsResponseDto> getCommentsInPost(@PathVariable Long postId) {
        return commentService.getCommentsInPost(postId);
    }
}
