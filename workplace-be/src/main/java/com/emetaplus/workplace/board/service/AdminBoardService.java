package com.emetaplus.workplace.board.service;

import com.emetaplus.workplace.board.model.AdminBoard;
import com.emetaplus.workplace.board.repository.AdminBoardRepository;
import com.emetaplus.workplace.core.exception.ExceptionHelper;
import com.emetaplus.workplace.security.jwt.UserUtils;
import com.emetaplus.workplace.userworkplace.model.UserWorkplace;
import com.emetaplus.workplace.userworkplace.model.UserWorkplaceSettings;
import com.emetaplus.workplace.userworkplace.repository.UserWorkplaceRepository;
import com.emetaplus.workplace.userworkplace.service.AdminWorkplaceService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminBoardService {

    private final AdminBoardRepository adminBoardRepository;
    private final UserWorkplaceRepository userWorkplaceRepository;

    @Value("${admin.host}")
    private String adminHost;

    @Value("${workplace.auth.public}")
    private String publicKey;

    @Value("${workplace.auth.private}")
    private String privateKey;

    private static String TOKEN = "";

    private final ObjectMapper mapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    private final AdminWorkplaceService adminWorkplaceService;

    public void addBoard(AdminBoard adminBoard) {
        UUID workplaceId = UserUtils.getCurrentUser().getWorkplaceId();
        UserWorkplace workplace = userWorkplaceRepository.findById(workplaceId).orElseThrow(() -> ExceptionHelper.getNotFoundException(UserWorkplace.class));
        UserWorkplaceSettings settings = adminWorkplaceService.getSettings(workplace.getWorkplaceId());
        if (settings.getFreeBoardsUsed() + 1 > settings.getFreeBoardsCount()) {
            throw ExceptionHelper.getInvalidFieldException(AdminBoard.class, "limit");
        }
        adminBoard.setWorkplaceId(workplaceId);
        adminWorkplaceService.increaseFreeBoardsUsedCount(workplace.getWorkplaceId());
        adminBoardRepository.save(adminBoard);
        userWorkplaceRepository.save(workplace);
    }

    public AdminBoard updateAdminBoard(UUID boardId, String name, String description) {
        AdminBoard existingBoard = adminBoardRepository.findById(boardId).orElseThrow(() -> ExceptionHelper.getNotFoundException(AdminBoard.class));
        UserUtils.isUserHasAccess(existingBoard.getWorkplaceId());
        existingBoard.setCustomName(name);
        existingBoard.setCustomDescription(description);
        return adminBoardRepository.save(existingBoard);
    }

    public List<AdminBoard> getAllBoardsFromAdmin() throws IOException {
        login();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", TOKEN);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        final var response = restTemplate.exchange(getBoardsPath(), HttpMethod.GET, entity, String.class);
        JsonNode root = mapper.readTree(response.getBody());
        ObjectReader reader = mapper.readerFor(new TypeReference<List<AdminBoard>>() {});
        return reader.readValue(root);
    }

    public List<AdminBoard> getAllSelectedBoards() {
        return adminBoardRepository.findAllByWorkplaceId(UserUtils.getCurrentUser().getWorkplaceId());
    }

    public Map<String, Integer> getAdminBoardsStats() {
        UUID workplaceId = UserUtils.getCurrentUser().getWorkplaceId();
        UserWorkplace workplace = userWorkplaceRepository.findById(workplaceId).orElseThrow(() -> ExceptionHelper.getNotFoundException(UserWorkplace.class));
        UserWorkplaceSettings settings = adminWorkplaceService.getSettings(workplace.getWorkplaceId());
        return Map.of(
                "current",settings.getFreeBoardsUsed(),
                "limit", settings.getFreeBoardsCount()
        );
    }

    public boolean isBoardInCollection(UUID adminId) {
        return adminBoardRepository.existsAdminBoardByWorkplaceIdAndAdminId(UserUtils.getCurrentUser().getWorkplaceId(), adminId);
    }

    private void login() {
        if (StringUtils.isNotBlank(TOKEN)) {
            return;
        }
        HttpHeaders loginHeaders = new HttpHeaders();
        loginHeaders.add("public-key", publicKey);
        loginHeaders.add("private-key", privateKey);
        HttpEntity<String> loginEntity = new HttpEntity<>("body", loginHeaders);
        final var loginResponse = restTemplate.exchange(getLoginPath(), HttpMethod.POST, loginEntity, String.class);
        if (loginResponse.getStatusCode().equals(HttpStatus.OK)) {
            setToken(loginResponse.getBody());
        }
    }

    private String getLoginPath() {
        return String.format("%sapi/export/login", adminHost);
    }

    private String getBoardsPath() {
        return String.format("%sapi/export/board/get/all", adminHost);
    }

    public static void setToken(String token) {
        TOKEN = "Bearer " + token;
    }
}
