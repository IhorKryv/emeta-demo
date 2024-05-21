package com.emetaplus.admin.deck.controller;

import com.emetaplus.admin.deck.dto.CardDTO;
import com.emetaplus.admin.deck.dto.CardWithSizeDTO;
import com.emetaplus.admin.deck.dto.ExportDeckDTO;
import com.emetaplus.admin.deck.mapper.CardMapper;
import com.emetaplus.admin.deck.mapper.ExportDeckMapper;
import com.emetaplus.admin.deck.service.DeckService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/export/deck")
@RequiredArgsConstructor
public class ExportDeckController {

    private static final String GET_ALL_DECKS_FOR_WORKPLACE_PATH = "get/all";
    private static final String GET_SELECTED_DECKS_FOR_WORKPLACE_PATH = "get/selected";

    private static final String GET_SINGLE_DECK_FOR_WORKPLACE_PATH = "get/{id}";
    private static final String GET_ALL_CARDS_OF_DECK_FOR_WORKPLACE_PATH = "{deckId}/cards/get/all";
    private static final String GET_ALL_CARDS_WITH_SIZE_OF_DECK_FOR_WORKPLACE_PATH = "{deckId}/cards/get/all-with-size";

    private final DeckService deckService;

    private final ExportDeckMapper deckMapper;

    private final CardMapper cardMapper;

    @GetMapping(GET_ALL_DECKS_FOR_WORKPLACE_PATH)
    public List<ExportDeckDTO> getAllDecksForWorkplace() {
        return deckMapper.toDeckDTOList(deckService.getAllDecksForWorkplace());
    }

    @PostMapping(GET_SELECTED_DECKS_FOR_WORKPLACE_PATH)
    public List<ExportDeckDTO> getSelectedDecks(@RequestBody List<UUID> selectedDeckIds) {
        return deckMapper.toDeckDTOList(deckService.getSelectedDecksForWorkplace(selectedDeckIds));
    }

    @GetMapping(GET_SINGLE_DECK_FOR_WORKPLACE_PATH)
    public ExportDeckDTO getSingleDeck(@PathVariable UUID id) {
        return deckMapper.toDTO(deckService.getSingleDeck(id));
    }

    @GetMapping(GET_ALL_CARDS_OF_DECK_FOR_WORKPLACE_PATH)
    public List<CardDTO> getAllCardsForDeck(@PathVariable UUID deckId) {
        return cardMapper.toCardListDTO(deckService.getAllCardsInDeckForWorkplace(deckId));
    }

    @GetMapping(GET_ALL_CARDS_WITH_SIZE_OF_DECK_FOR_WORKPLACE_PATH)
    public List<CardWithSizeDTO> getAllCardsWithSizeForDeck(@PathVariable UUID deckId) {
        return cardMapper.toCardWithSizeDTOList(deckService.getAllCardsInDeckForWorkplace(deckId));
    }

}
