package com.emetaplus.admin.deck.controller;

import com.emetaplus.admin.core.dto.PageDTO;
import com.emetaplus.admin.core.query.PageableRequestQuery;
import com.emetaplus.admin.deck.dto.CardDTO;
import com.emetaplus.admin.deck.dto.DeckDTO;
import com.emetaplus.admin.deck.mapper.CardMapper;
import com.emetaplus.admin.deck.mapper.DeckMapper;
import com.emetaplus.admin.deck.model.Card;
import com.emetaplus.admin.deck.model.Deck;
import com.emetaplus.admin.deck.service.DeckService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/deck")
@RequiredArgsConstructor
public class DeckController {

    private static final String CREATE_DECK_PATH = "create";
    private static final String UPDATE_DECK_PATH = "update/{id}";
    private static final String UPDATE_DECK_CARDBACK_PATH = "update/{id}/cardback";
    private static final String MAKE_DECK_CARD_AS_CARDBACK_PATH = "{deckId}/swap/{cardId}/{extension}";
    private static final String GET_DECK_PATH = "get/{id}";
    private static final String GET_ALL_DECKS_PATH = "get/all";
    private static final String DELETE_DECK_PATH = "delete/{id}";
    private static final String REMOVE_CARDBACK_FROM_DECK_PATH = "delete/{id}/cardback";
    private static final String ADD_CARD_TO_DECK_PATH = "{deckId}/card/add";
    private static final String GET_ALL_DECK_CARDS_PATH = "{deckId}/card/all";
    private static final String REMOVE_CARD_FROM_DECK_PATH = "{deckId}/card/remove/{cardId}/{extension}";

    private final DeckService deckService;
    private final DeckMapper deckMapper;
    private final CardMapper cardMapper;

    @PostMapping(CREATE_DECK_PATH)
    public DeckDTO createDeck(@RequestBody DeckDTO deckDTO) {
        return deckMapper.toDTO(deckService.createDeck(deckMapper.toEntity(deckDTO)));
    }

    @PostMapping(ADD_CARD_TO_DECK_PATH)
    public void addCardsToDeck(@PathVariable UUID deckId,
                               @RequestParam(name = "cards") List<MultipartFile> cards) {
        deckService.addCardsToDeck(cards, deckId);
    }

    @PutMapping(UPDATE_DECK_PATH)
    public DeckDTO updateDeck(@PathVariable UUID id, @RequestBody DeckDTO deckDTO) {
        return deckMapper.toDTO(deckService.updateDeck(id, deckMapper.toEntity(deckDTO)));
    }

    @PutMapping(UPDATE_DECK_CARDBACK_PATH)
    public void updateDeckCardback(@PathVariable UUID id, @RequestParam(name = "file") MultipartFile file) {
        deckService.updateCardback(id, file);
    }

    @GetMapping(MAKE_DECK_CARD_AS_CARDBACK_PATH)
    public void makeDeckCardAsCardback(@PathVariable UUID deckId, @PathVariable UUID cardId, @PathVariable String extension) {
        deckService.makeCardAsCardback(deckId, cardId, extension);
    }

    @GetMapping(GET_DECK_PATH)
    public DeckDTO getSingleDeck(@PathVariable UUID id) {
        return deckMapper.toDTO(deckService.getSingleDeck(id));
    }

    @GetMapping(GET_ALL_DECKS_PATH)
    public PageDTO<DeckDTO> getAllDecks(PageableRequestQuery pageableRequestQuery) {
        return deckMapper.toDeckPageDTO(deckService.getAllDecks(pageableRequestQuery));
    }

    @GetMapping(GET_ALL_DECK_CARDS_PATH)
    public PageDTO<CardDTO> getAllCardsInDeck(@PathVariable UUID deckId, PageableRequestQuery pageableRequestQuery) {
        return cardMapper.toCardPageDTO(deckService.getAllCardsInDeck(deckId, pageableRequestQuery));
    }

    @DeleteMapping(DELETE_DECK_PATH)
    public void deleteDeck(@PathVariable UUID id) {
        deckService.deleteDeckAndAllCardsInside(id);
    }

    @DeleteMapping(REMOVE_CARD_FROM_DECK_PATH)
    public void removeCardFromDeck(@PathVariable UUID deckId, @PathVariable UUID cardId, @PathVariable String extension) {
        deckService.removeCardFromDeck(deckId, cardId, extension);
    }

    @DeleteMapping(REMOVE_CARDBACK_FROM_DECK_PATH)
    public void removeCardbackFromDeck(@PathVariable UUID id) {
        deckService.removeCardbackFromDeck(id);
    }

}
