package com.emetaplus.admin.board.service;

import com.emetaplus.admin.board.model.Board;
import com.emetaplus.admin.board.repository.BoardRepository;
import com.emetaplus.admin.core.exception.ExceptionHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardValidator {

    private final BoardRepository boardRepository;

    public void validateBoardForCreation(Board board) {
        if (!isBoardNameAvailable(null, board.getName())) {
            throw ExceptionHelper.getInvalidFieldException(Board.class, "name");
        }
    }

    public void validateBoardForUpdate(UUID existingId, String name) {
        if (!isBoardNameAvailable(existingId, name)) {
            throw ExceptionHelper.getInvalidFieldException(Board.class, "name");
        }
    }

    private boolean isBoardNameAvailable(UUID id, String name) {
        Board board = boardRepository.findOneByName(name).orElse(null);
        if (Objects.isNull(board)) {
            return true;
        }
        return board.getId().equals(id);
    }
}
