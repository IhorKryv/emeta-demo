package com.emetaplus.workplace.userdeck.service;

import com.emetaplus.workplace.core.exception.ExceptionHelper;
import com.emetaplus.workplace.security.jwt.UserUtils;
import com.emetaplus.workplace.userdeck.dto.CardDTO;
import com.emetaplus.workplace.userdeck.dto.CardWithSizeDTO;
import com.emetaplus.workplace.userdeck.model.AdminDeck;
import com.emetaplus.workplace.userdeck.repository.AdminDeckRepository;
import com.emetaplus.workplace.userworkplace.model.UserWorkplace;
import com.emetaplus.workplace.userworkplace.model.UserWorkplaceSettings;
import com.emetaplus.workplace.userworkplace.repository.UserWorkplaceRepository;
import com.emetaplus.workplace.userworkplace.service.AdminWorkplaceService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminDeckService {

    @Value("${admin.host}")
    private String adminHost;

    @Value("${workplace.auth.public}")
    private String publicKey;

    @Value("${workplace.auth.private}")
    private String privateKey;

    private static String TOKEN = "";

    private final ObjectMapper mapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    private final UserWorkplaceRepository userWorkplaceRepository;
    private final AdminDeckRepository adminDeckRepository;

    private final AdminWorkplaceService adminWorkplaceService;

    @Transactional
    public void addDeck(AdminDeck adminDeck) {
        UUID workplaceId = UserUtils.getCurrentUser().getWorkplaceId();
        UserWorkplace workplace = userWorkplaceRepository.findById(workplaceId).orElseThrow(() -> ExceptionHelper.getNotFoundException(UserWorkplace.class));
        UserWorkplaceSettings settings = adminWorkplaceService.getSettings(workplace.getWorkplaceId());
        if (settings.getFreeDecksUsed() + 1 > settings.getFreeDecksCount()) {
            throw ExceptionHelper.getInvalidFieldException(AdminDeck.class, "limit");
        }
        adminDeck.setWorkplaceId(workplaceId);
        adminWorkplaceService.increaseFreeDecksUsedCount(workplace.getWorkplaceId());
        adminDeckRepository.save(adminDeck);
        userWorkplaceRepository.save(workplace);
    }

    public AdminDeck updateAdminDeck(UUID deckId, AdminDeck adminDeck) {
        AdminDeck existingDeck = getAdminDeck(deckId);
        UserUtils.isUserHasAccess(existingDeck.getWorkplaceId());
        existingDeck.setCustomName(adminDeck.getCustomName());
        existingDeck.setCustomDescription(adminDeck.getCustomDescription());
        return adminDeckRepository.save(existingDeck);
    }

    public List<AdminDeck> getAllDefaultDecks() throws IOException {
        login();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", TOKEN);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        final var response = restTemplate.exchange(getDecksPath(), HttpMethod.GET, entity, String.class);
        JsonNode root = mapper.readTree(response.getBody());
        ObjectReader reader = mapper.readerFor(new TypeReference<List<AdminDeck>>() {});
        return reader.readValue(root);
    }

    public List<CardDTO> getAllCardsForDeck(UUID deckId) throws IOException {
        login();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", TOKEN);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        final var response = restTemplate.exchange(getCardsPath(deckId.toString()), HttpMethod.GET, entity, String.class);
        JsonNode root = mapper.readTree(response.getBody());
        ObjectReader reader = mapper.readerFor(new TypeReference<List<CardDTO>>() {});
        return reader.readValue(root);
    }

    public List<CardWithSizeDTO> getAllCardsWithSizeForDeck(UUID deckId) throws IOException {
        login();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", TOKEN);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        final var response = restTemplate.exchange(getCardsWithSizePath(deckId.toString()), HttpMethod.GET, entity, String.class);
        JsonNode root = mapper.readTree(response.getBody());
        ObjectReader reader = mapper.readerFor(new TypeReference<List<CardWithSizeDTO>>() {});
        return reader.readValue(root);
    }

    public List<AdminDeck> getAllSelectedDecks() {
        return adminDeckRepository.findAllByWorkplaceId(UserUtils.getCurrentUser().getWorkplaceId());
    }

    public Map<String, Integer> getAdminDecksStats() {
        UUID workplaceId = UserUtils.getCurrentUser().getWorkplaceId();
        UserWorkplace workplace = userWorkplaceRepository.findById(workplaceId).orElseThrow(() -> ExceptionHelper.getNotFoundException(UserWorkplace.class));
        UserWorkplaceSettings settings = adminWorkplaceService.getSettings(workplace.getWorkplaceId());
        return Map.of(
                "current", settings.getFreeDecksUsed(),
                "limit", settings.getFreeDecksCount()
        );
    }

    public boolean isDeckInCollection(UUID adminId) {
        return adminDeckRepository.existsAdminDeckByWorkplaceIdAndAdminId(UserUtils.getCurrentUser().getWorkplaceId(), adminId);
    }


    public AdminDeck getSingleAdminDeck(UUID deckAdminId) throws IOException {
        login();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", TOKEN);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        final var response = restTemplate.exchange(getSingleDeckPathPath(deckAdminId), HttpMethod.GET, entity, String.class);
        JsonNode root = mapper.readTree(response.getBody());
        ObjectReader reader = mapper.readerFor(new TypeReference<AdminDeck>() {});
        return reader.readValue(root);
    }

    private AdminDeck getAdminDeck(UUID id) {
        AdminDeck adminDeck = adminDeckRepository.findById(id).orElseThrow(() -> ExceptionHelper.getNotFoundException(AdminDeck.class));
        UserUtils.isUserHasAccess(adminDeck.getWorkplaceId());
        return adminDeck;
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

    private String getDecksPath() {
        return String.format("%sapi/export/deck/get/all", adminHost);
    }

    private String getSingleDeckPathPath(UUID id) {
        return String.format("%sapi/export/deck/get/%s", adminHost, id);
    }

    private String getCardsPath(String deckId) {
        return adminHost + "api/export/deck/" + deckId + "/cards/get/all";
    }

    private String getCardsWithSizePath(String deckId) {
        return adminHost + "api/export/deck/" + deckId + "/cards/get/all-with-size";
    }

    public static void setToken(String token) {
        TOKEN = "Bearer " + token;
    }

}
