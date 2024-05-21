package com.emetaplus.workplace.userdeck.controller;

import com.emetaplus.workplace.core.dto.PageDTO;
import com.emetaplus.workplace.core.query.PageableRequestQuery;
import com.emetaplus.workplace.userdeck.dto.CardDTO;
import com.emetaplus.workplace.userdeck.dto.UserDeckDTO;
import com.emetaplus.workplace.userdeck.mapper.CardMapper;
import com.emetaplus.workplace.userdeck.mapper.UserDeckMapper;
import com.emetaplus.workplace.userdeck.service.UserDeckService;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/user-deck")
@RequiredArgsConstructor
public class UserDeckController {

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
    private static final String REMOVE_CARD_FROM_DECK_PATH = "{deckId}/card/remove/{cardId}";

    private final UserDeckService deckService;
    private final UserDeckMapper deckMapper;
    private final CardMapper cardMapper;

    @PostMapping(CREATE_DECK_PATH)
    public UserDeckDTO createDeck(@RequestBody UserDeckDTO deckDTO) {
        return deckMapper.toDTO(deckService.createUserDeck(deckMapper.toEntity(deckDTO)));
    }

    @PostMapping(ADD_CARD_TO_DECK_PATH)
    public void addCardsToDeck(@PathVariable UUID deckId,
                               @RequestParam(name = "cards") List<MultipartFile> cards) {
        deckService.addCardsToUserDeck(cards, deckId);
    }

    @PutMapping(UPDATE_DECK_PATH)
    public UserDeckDTO updateDeck(@PathVariable UUID id, @RequestBody UserDeckDTO deckDTO) {
        return deckMapper.toDTO(deckService.updateUserDeck(id, deckMapper.toEntity(deckDTO)));
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
    public UserDeckDTO getSingleDeck(@PathVariable UUID id) {
        return deckMapper.toDTO(deckService.getSingleDeck(id));
    }

    @GetMapping(GET_ALL_DECKS_PATH)
    public List<UserDeckDTO> getAllDecks() {
        return deckMapper.toListDTO(deckService.getAllDecks());
    }

    @GetMapping(GET_ALL_DECK_CARDS_PATH)
    public PageDTO<CardDTO> getAllCardsInDeck(@PathVariable UUID deckId, PageableRequestQuery pageableRequestQuery) {
        return cardMapper.toCardPageDTO(deckService.getAllCardsInDeck(deckId, pageableRequestQuery));
    }

    @DeleteMapping(DELETE_DECK_PATH)
    public void deleteDeck(@PathVariable UUID id) {
        deckService.deleteUserDeckAndAllCardsInside(id);
    }

    @DeleteMapping(REMOVE_CARD_FROM_DECK_PATH)
    public void removeCardFromDeck(@PathVariable UUID deckId, @PathVariable UUID cardId) {
        deckService.removeCardFromDeck(deckId, cardId);
    }

    @DeleteMapping(REMOVE_CARDBACK_FROM_DECK_PATH)
    public void removeCardbackFromDeck(@PathVariable UUID id) {
        deckService.removeCardbackFromDeck(id);
    }
}
