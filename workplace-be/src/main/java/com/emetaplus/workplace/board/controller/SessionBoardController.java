package com.emetaplus.workplace.board.controller;

import com.emetaplus.workplace.board.dto.SessionBoardDTO;
import com.emetaplus.workplace.board.mapper.SessionBoardMapper;
import com.emetaplus.workplace.board.service.SessionBoardService;
import com.emetaplus.workplace.core.dto.PageDTO;
import com.emetaplus.workplace.core.query.PageableRequestQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/session-board")
@RequiredArgsConstructor
public class SessionBoardController {

    private static final String CREATE_BOARD_PATH = "create";
    private static final String UPLOAD_IMAGE_TO_BOARD_PATH = "{id}/upload";
    private static final String UPDATE_BOARD_PATH = "update/{id}";
    private static final String GET_SINGLE_BOARD_PATH = "get/{id}";
    private static final String GET_ALL_BOARDS_PATH = "get/all";
    private static final String DELETE_BOARD_PATH = "delete/{id}";

    private final SessionBoardService sessionBoardService;
    private final SessionBoardMapper mapper;

    @PostMapping(CREATE_BOARD_PATH)
    public SessionBoardDTO createSessionBoard(@RequestBody SessionBoardDTO dto) {
        return mapper.toDTO(sessionBoardService.createSessionBoard(mapper.toEntity(dto)));
    }

    @PutMapping(UPLOAD_IMAGE_TO_BOARD_PATH)
    public SessionBoardDTO uploadSessionBoardImage(@PathVariable UUID id, @RequestParam(name = "file") MultipartFile file) {
        return mapper.toDTO(sessionBoardService.setSessionBoardImage(id, file));
    }

    @PutMapping(UPDATE_BOARD_PATH)
    public SessionBoardDTO updateSessionBoard(@PathVariable UUID id, @RequestBody SessionBoardDTO dto) {
        return mapper.toDTO(sessionBoardService.updateSessionBoard(id, mapper.toEntity(dto)));
    }

    @GetMapping(GET_SINGLE_BOARD_PATH)
    public SessionBoardDTO getSessionBoard(@PathVariable UUID id) {
        return mapper.toDTO(sessionBoardService.getSingleSessionBoard(id));
    }

    @GetMapping(GET_ALL_BOARDS_PATH)
    public PageDTO<SessionBoardDTO> getAllSessionBoards(PageableRequestQuery pageableRequestQuery) {
        return mapper.toBoardsPageDTO(sessionBoardService.getAllSessionBoards(pageableRequestQuery));
    }

    @DeleteMapping(DELETE_BOARD_PATH)
    public void deleteSessionBoard(@PathVariable UUID id) {
        sessionBoardService.deleteSessionBoard(id);
    }

}
