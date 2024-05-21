package com.emetaplus.workplace.board.controller;

import com.emetaplus.workplace.board.dto.AdminBoardDTO;
import com.emetaplus.workplace.board.dto.UpdateAdminBoardDTO;
import com.emetaplus.workplace.board.mapper.AdminBoardMapper;
import com.emetaplus.workplace.board.service.AdminBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin-board")
@RequiredArgsConstructor
public class AdminBoardController {

    private static final String ADD_ADMIN_BOARD_TO_WORKPLACE_PATH = "add";
    private static final String UPDATE_ADMIN_BOARD_PATH = "update/{boardId}";
    private static final String GET_ALL_ADMIN_BOARDS_PATH = "get/all";
    private static final String GET_SELECTED_ADMIN_BOARDS_PATH = "get/selected";
    private static final String GET_ADMIN_BOARDS_STATS_PATH = "get/stats";

    private final AdminBoardService adminBoardService;
    private final AdminBoardMapper adminBoardMapper;

    @PostMapping(ADD_ADMIN_BOARD_TO_WORKPLACE_PATH)
    public void addDeckToWorkplace(@RequestBody AdminBoardDTO adminBoardDTO) {
        adminBoardService.addBoard(adminBoardMapper.toEntity(adminBoardDTO));
    }

    @PutMapping(UPDATE_ADMIN_BOARD_PATH)
    public AdminBoardDTO updateAdminDeck(@PathVariable UUID boardId, @RequestBody UpdateAdminBoardDTO boardDTO) {
        return adminBoardMapper.toDTO(adminBoardService.updateAdminBoard(boardId, boardDTO.getName(), boardDTO.getDescription()));
    }

    @GetMapping(GET_ALL_ADMIN_BOARDS_PATH)
    public List<AdminBoardDTO> getAllAdminBoards() throws IOException {
        return adminBoardMapper.toAdminBoardsDTOList(adminBoardService.getAllBoardsFromAdmin());
    }

    @GetMapping(GET_SELECTED_ADMIN_BOARDS_PATH)
    public List<AdminBoardDTO> getSelectedAdminBoards() {
        return adminBoardMapper.toAdminBoardsDTOList(adminBoardService.getAllSelectedBoards());
    }

    @GetMapping(GET_ADMIN_BOARDS_STATS_PATH)
    public Map<String, Integer> getAdminBoardsStats() {
        return adminBoardService.getAdminBoardsStats();
    }
}
