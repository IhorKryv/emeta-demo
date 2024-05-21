package com.emetaplus.admin.deck.service;

import com.emetaplus.admin.core.exception.ExceptionHelper;
import com.emetaplus.admin.deck.model.Deck;
import com.emetaplus.admin.deck.repository.DeckRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeckValidator {

    private final DeckRepository deckRepository;

    public void validateDeckForCreate(Deck deck) {
        validateDeckName(null, deck.getName(), "create");
    }

    public void validateDeckForUpdate(UUID id, Deck deck) {
        validateDeckName(id, deck.getName(), "update");
    }

    private void validateDeckName(UUID id, String name, String method) {
        if (StringUtils.isBlank(name)) {
            log.error("[Deck Exception]: Can't {} a deck without name", method);
            throw ExceptionHelper.getInvalidFieldException(Deck.class, "name");
        }

        if(isDeckWithNameAlreadyExists(id, name)) {
            log.error("[Deck Exception]: Deck with name={} already exists", name);
            throw ExceptionHelper.getAlreadyExistsException(Deck.class, "name");
        }
    }

    private boolean isDeckWithNameAlreadyExists(UUID id, String name) {
        Deck deck = deckRepository.findOneByName(name).orElse(null);
        if (Objects.isNull(deck)) {
            return false;
        }
        return !deck.getId().equals(id);
    }
}
