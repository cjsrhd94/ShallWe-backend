package com.project.board.domain.post.web;

import com.project.board.domain.post.dto.PostDetailsQueryDto;
import com.project.board.domain.post.dto.PostsQueryDto;
import com.project.board.domain.post.dto.RecommendPostsInBoardQueryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;

public interface PostReader {

    Post getPostBy(Long id);
    Page<PostsQueryDto> getPostsInBoard(Long id, PageRequest pageRequest);
    Page<PostsQueryDto> getPostsInPostCategory(Long id, PageRequest pageRequest);
    Page<PostsQueryDto> getPostsByPostTitleInBoard(Long id, String title, PageRequest pageRequest);
    Page<PostsQueryDto> getPostsByPostContentInBoard(Long id, String content, PageRequest pageRequest);
    Page<PostsQueryDto> getPostsByPostTitleOrPostContentInBoard(Long id, String keyword, PageRequest pageRequest);
    Page<PostsQueryDto> getPostsByUserNicknameInBoard(Long id, String keyword, PageRequest pageRequest);
    PostDetailsQueryDto getPostDetails(Long id);
    Page<RecommendPostsInBoardQueryDto> getRecommendPostsInBoard(Long id, LocalDateTime now,
                                                                 LocalDateTime twelveHoursAgo,
                                                                 PageRequest pageRequest);
}
