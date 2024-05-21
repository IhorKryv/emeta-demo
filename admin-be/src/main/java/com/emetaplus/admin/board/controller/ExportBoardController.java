package com.emetaplus.admin.board.controller;

import com.emetaplus.admin.board.dto.ExportBoardDTO;
import com.emetaplus.admin.board.mapper.ExportBoardMapper;
import com.emetaplus.admin.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/export/board")
@RequiredArgsConstructor
public class ExportBoardController {

    private static final String GET_ALL_BOARDS_FOR_EXPORT_PATH = "get/all";
    private static final String GET_SELECTED_BOARDS_FOR_WORKPLACE_PATH = "get/selected";
    private static final String GET_SINGLE_BOARD_FOR_EXPORT_PATH = "get/{boardId}";

    private final BoardService boardService;
    private final ExportBoardMapper exportBoardMapper;


    @GetMapping(GET_ALL_BOARDS_FOR_EXPORT_PATH)
    public List<ExportBoardDTO> getAllBoardsForExport() {
        return exportBoardMapper.toDTOList(boardService.getAllBoards());
    }

    @GetMapping(GET_SINGLE_BOARD_FOR_EXPORT_PATH)
    public ExportBoardDTO getBoardForExport(@PathVariable UUID boardId) {
        return exportBoardMapper.toDTO(boardService.getBoard(boardId));
    }

}
