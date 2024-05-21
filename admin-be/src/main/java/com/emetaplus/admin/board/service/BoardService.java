package com.emetaplus.admin.board.service;

import com.emetaplus.admin.board.model.Board;
import com.emetaplus.admin.board.repository.BoardRepository;
import com.emetaplus.admin.core.exception.ExceptionHelper;
import com.emetaplus.admin.core.minio.MinioService;
import com.emetaplus.admin.deck.model.Card;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final MinioService minioService;
    private final BoardValidator boardValidator;

    @Transactional
    public Board createBoard(String name, String description, MultipartFile file) {
        Board board = new Board();
        board.setName(name);
        board.setDescription(description);
        boardValidator.validateBoardForCreation(board);
        board = boardRepository.save(board);
        addBoardImage(board, file);
        return boardRepository.save(board);
    }

    @Transactional
    public Board updateBoard(UUID id, String name, String description, MultipartFile file) {
        Board existingBoard = getBoardById(id);
        if (Objects.nonNull(existingBoard.getImage())) {
            removeBoardImage(existingBoard.getId());
        }
        boardValidator.validateBoardForUpdate(existingBoard.getId(), name);
        addBoardImage(existingBoard, file);
        existingBoard.setName(name);
        existingBoard.setDescription(description);
        return boardRepository.save(existingBoard);
    }

    public Board getBoard(UUID id) {
        return getBoardById(id);
    }

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    @Transactional
    public void deleteBoard(UUID boardId) {
        Board board = getBoardById(boardId);
        minioService.deleteBoardImage(board.getImage());
        boardRepository.delete(board);
    }

    private Board getBoardById(UUID id) {
        return boardRepository.findById(id).orElseThrow(() -> ExceptionHelper.getNotFoundException(Board.class));
    }


    private void addBoardImage(Board board, MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        try {
            minioService.addBoardImage(
                    board.getId().toString(),
                    extension,
                    new ByteArrayInputStream(file.getBytes()),
                    file.getSize()
            );
            board.setImage(board.getId() + "." + extension);
        } catch (Exception e) {
            throw ExceptionHelper.getInternalServerError();
        }
    }

    private void removeBoardImage(UUID boardId) {
        Board board = getBoardById(boardId);
        minioService.deleteBoardImage(board.getImage());
    }
}
