package com.emetaplus.workplace.userdeck.controller;

import com.emetaplus.workplace.userdeck.dto.AdminDeckDTO;
import com.emetaplus.workplace.userdeck.dto.CardDTO;
import com.emetaplus.workplace.userdeck.dto.CardWithSizeDTO;
import com.emetaplus.workplace.userdeck.mapper.AdminDeckMapper;
import com.emetaplus.workplace.userdeck.service.AdminDeckService;
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
@RequestMapping("/api/admin-decks")
@RequiredArgsConstructor
public class AdminDeckController {

    private final AdminDeckService adminDeckService;

    private static final String GET_ADMIN_DECKS_PATH = "all";

    private static final String GET_ADMIN_DECK_BY_ID = "/{deckAdminId}";
    private static final String GET_ADMIN_DECK_CARDS_PATH = "{deckAdminId}/cards";
    private static final String GET_SELECTED_DECKS_PATH = "selected";
    private static final String GET_ADMIN_DECK_STATS_PATH = "stats";
    private static final String ADD_ADMIN_DECK_TO_WORKPLACE_PATH = "add";
    private static final String UPDATE_ADMIN_DECK_PATH = "update/{deckId}";
    private static final String REMOVE_ADMIN_DECK_FROM_WORKPLACE_PATH = "remove/{deckId}";

    private final AdminDeckMapper adminDeckMapper;


    @PostMapping(ADD_ADMIN_DECK_TO_WORKPLACE_PATH)
    public void addDeckToWorkplace(@RequestBody AdminDeckDTO adminDeckDTO) {
        adminDeckService.addDeck(adminDeckMapper.toEntity(adminDeckDTO));
    }

    @PutMapping(UPDATE_ADMIN_DECK_PATH)
    public AdminDeckDTO updateAdminDeck(@PathVariable UUID deckId, @RequestBody AdminDeckDTO adminDeckDTO) {
        return adminDeckMapper.toDTO(adminDeckService.updateAdminDeck(deckId, adminDeckMapper.toEntity(adminDeckDTO)));
    }

    @GetMapping(GET_ADMIN_DECKS_PATH)
    public List<AdminDeckDTO> getDefaultDecks() throws IOException {
        return adminDeckMapper.toDeckListDTO(adminDeckService.getAllDefaultDecks());
    }

    @GetMapping(GET_ADMIN_DECK_CARDS_PATH)
    public List<CardDTO> getCardsByDeck(@PathVariable UUID deckAdminId) throws IOException {
        return adminDeckService.getAllCardsForDeck(deckAdminId);
    }

    @GetMapping(GET_SELECTED_DECKS_PATH)
    public List<AdminDeckDTO> getSelectedDecks() {
        return adminDeckMapper.toDeckListDTO(adminDeckService.getAllSelectedDecks());
    }

    @GetMapping(GET_ADMIN_DECK_BY_ID)
    public AdminDeckDTO getAdminDeckById(@PathVariable UUID deckAdminId) throws IOException {
        return adminDeckMapper.toDTO(adminDeckService.getSingleAdminDeck(deckAdminId));
    }

    @GetMapping(GET_ADMIN_DECK_STATS_PATH)
    public Map<String, Integer> getAdminDeckStats() {
        return adminDeckService.getAdminDecksStats();
    }

}
