package com.project.board.domain.board.web;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.board.domain.post.web.PostCategory;
import com.project.board.global.audit.BaseEntity;
import com.project.board.global.exception.InvalidParamException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board_table")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 게시판 제목
    @NotNull
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("boardcategory-board")
    @JoinColumn(name = "board_category_id")
    private BoardCategory boardCategory;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    @JsonManagedReference("board-postcategory")
    private List<PostCategory> postCategories = new ArrayList<>();

    @Builder
    public Board(Long id, String title, BoardCategory boardCategory, List<PostCategory> postCategories) {
        this.id = id;
        this.title = title;
        this.boardCategory = boardCategory;
        this.postCategories = postCategories;
    }

    public void update(String title) {
        if (StringUtils.isEmpty(title)) throw new InvalidParamException("Board.title");

        this.title = title;
    }
}
