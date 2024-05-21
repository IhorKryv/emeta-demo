package com.emetaplus.admin.board.controller;

import com.emetaplus.admin.board.dto.BoardDTO;
import com.emetaplus.admin.board.dto.CreateBoardDTO;
import com.emetaplus.admin.board.mapper.BoardMapper;
import com.emetaplus.admin.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private static final String CREATE_BOARD_PATH = "create";
    private static final String UPDATE_BOARD_PATH = "update/{id}";
    private static final String GET_BOARD_PATH = "get/{id}";
    private static final String GET_ALL_BOARDS_PATH = "get/all";
    private static final String DELETE_BOARD_PATH = "delete/{id}";

    private static final String ADD_IMAGE_TO_BOARD_PATH = "{id}/image/add";
    private static final String REMOVE_IMAGE_FROM_BOARD_PATH = "{id}/image/remove";

    private final BoardService boardService;
    private final BoardMapper boardMapper;

    @PostMapping(value = CREATE_BOARD_PATH, consumes = {"multipart/form-data"}, produces = "application/json" )
    public BoardDTO createBoard(@ModelAttribute CreateBoardDTO boardDTO) {
        return boardMapper.toDTO(boardService.createBoard(boardDTO.getName(), boardDTO.getDescription(), boardDTO.getFile()));
    }

    @PutMapping(value =UPDATE_BOARD_PATH, consumes = {"multipart/form-data"}, produces = "application/json")
    public BoardDTO updateBoard(@PathVariable UUID id, @ModelAttribute CreateBoardDTO boardDTO) {
        return boardMapper.toDTO(boardService.updateBoard(id, boardDTO.getName(), boardDTO.getDescription(), boardDTO.getFile()));
    }

    @GetMapping(GET_BOARD_PATH)
    public BoardDTO getBoard(@PathVariable UUID id) {
        return boardMapper.toDTO(boardService.getBoard(id));
    }

    @GetMapping(GET_ALL_BOARDS_PATH)
    public List<BoardDTO> getAllBoards() {
        return boardMapper.toDTOList(boardService.getAllBoards());
    }

    @DeleteMapping(DELETE_BOARD_PATH)
    public void deleteBoard(@PathVariable UUID id) {
        boardService.deleteBoard(id);
    }

}
