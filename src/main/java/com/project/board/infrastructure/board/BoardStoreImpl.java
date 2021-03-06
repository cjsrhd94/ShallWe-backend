package com.project.board.infrastructure.board;

import com.project.board.domain.board.web.Board;
import com.project.board.domain.board.web.BoardStore;
import com.project.board.global.exception.InvalidParamException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardStoreImpl implements BoardStore {

    private final BoardRepository boardRepository;

    public Board store(Board board) {
        validCheck(board);
        return boardRepository.save(board);
    }

    public void delete(Board board) {
        boardRepository.delete(board);
    }

    private void validCheck(Board board) {
        if (board.getBoardCategory() == null) throw new InvalidParamException("Board.boardCategory");
        if (StringUtils.isEmpty(board.getTitle())) throw new InvalidParamException("Board.title");
    }
}
