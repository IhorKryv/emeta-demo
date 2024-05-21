package com.emetaplus.workplace.userworkplace.service;

import com.emetaplus.workplace.core.exception.ExceptionHelper;
import com.emetaplus.workplace.userdeck.model.AdminDeck;
import com.emetaplus.workplace.userworkplace.dto.WorkplaceAdminDataDTO;
import com.emetaplus.workplace.userworkplace.model.UserWorkplace;
import com.emetaplus.workplace.userworkplace.model.UserWorkplaceSettings;
import com.emetaplus.workplace.userworkplace.model.WorkplaceStatus;
import com.emetaplus.workplace.userworkplace.repository.UserWorkplaceRepository;
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
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminWorkplaceService {

    private final List<WorkplaceStatus> allowedStatuses = List.of(
            WorkplaceStatus.NEW,
            WorkplaceStatus.TRIAL,
            WorkplaceStatus.ACTIVATED,
            WorkplaceStatus.TEMPORARY
    );

    @Value("${admin.host}")
    private String adminHost;

    @Value("${workplace.auth.public}")
    private String publicKey;

    @Value("${workplace.auth.private}")
    private String privateKey;

    private final ObjectMapper mapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    private static String TOKEN = "";

    private final UserWorkplaceRepository userWorkplaceRepository;

    public void createWorkplaceOnAdmin(UserWorkplace workplace, WorkplaceAdminDataDTO dto) {
        login(false);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            String body = mapper.writeValueAsString(dto);
            HttpEntity<String> entity = new HttpEntity<>(body, headers);
            restTemplate.postForObject(getCreateWorkplacePath(), entity, String.class);
        } catch (Exception e) {
            userWorkplaceRepository.delete(workplace);
            throw ExceptionHelper.getInternalServerError();
        }
    }

    public UserWorkplaceSettings getSettings(UUID workplaceId) {
        login(false);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", TOKEN);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        final var response = restTemplate.exchange(getSettingsPath(workplaceId.toString()), HttpMethod.GET, entity, String.class);
        try {
            JsonNode root = mapper.readTree(response.getBody());
            ObjectReader reader = mapper.readerFor(new TypeReference<UserWorkplaceSettings>() {
            });
            return reader.readValue(root);
        } catch (Exception e) {
            throw ExceptionHelper.getInternalServerError();
        }
    }

    public void increaseFreeDecksUsedCount(UUID workplaceId) {
        login(false);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", TOKEN);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        restTemplate.exchange(getIncreaseUsedDecksCountPath(workplaceId.toString()), HttpMethod.GET, entity, String.class);

    }

    public void increaseFreeBoardsUsedCount(UUID workplaceId) {
        login(false);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", TOKEN);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        restTemplate.exchange(getIncreaseUsedBoardsCountPath(workplaceId.toString()), HttpMethod.GET, entity, String.class);

    }

    public boolean isWorkplaceAvailable(UUID workplaceId) {
        login(false);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", TOKEN);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        final var statusResponse = restTemplate.exchange(getStatusPath(workplaceId.toString()), HttpMethod.GET, entity, WorkplaceStatus.class);
        return allowedStatuses.contains(statusResponse.getBody());
    }

    private void login(boolean expired) {
        if (!expired && StringUtils.isNotBlank(TOKEN)) {
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

    private String getCreateWorkplacePath() {
        return String.format("%sapi/export/workplace/create", adminHost);
    }

    private String getSettingsPath(String workplaceId) {
        return String.format("%sapi/export/workplace/%s/settings", adminHost, workplaceId);
    }

    private String getIncreaseUsedDecksCountPath(String workplaceId) {
        return String.format("%sapi/export/workplace/%s/decks/increase", adminHost, workplaceId);
    }

    private String getIncreaseUsedBoardsCountPath(String workplaceId) {
        return String.format("%sapi/export/workplace/%s/boards/increase", adminHost, workplaceId);
    }

    private String getStatusPath(String workplaceId) {
        return String.format("%sapi/export/workplace/%s/status", adminHost, workplaceId);
    }

    public static void setToken(String token) {
        TOKEN = "Bearer " + token;
    }

    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000)
    protected void generateNewToken() {
        log.info("Creating the new token for services connections");
        login(true);
    }
}
