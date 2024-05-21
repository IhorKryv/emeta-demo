package com.emetaplus.workplace.userdeck.service;

import com.emetaplus.workplace.core.exception.ExceptionHelper;
import com.emetaplus.workplace.userdeck.model.UserDeck;
import com.emetaplus.workplace.userdeck.repository.UserDeckRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDeckValidator {

    private final UserDeckRepository userDeckRepository;

    public void validateUserDeckForCreate(UUID workplaceId, UserDeck userDeck) {
        validateDeckName(workplaceId, null, userDeck.getName(), "create");
    }

    public void validateUserDeckForUpdate(UUID workplaceId, UserDeck userDeck) {
        validateDeckName(workplaceId, userDeck.getId(), userDeck.getName(), "update");
    }

    private void validateDeckName(UUID workplaceId, UUID id, String name, String method) {
        if (StringUtils.isBlank(name)) {
            log.error("[Deck Exception]: Can't {} a deck without name", method);
            throw ExceptionHelper.getInvalidFieldException(UserDeck.class, "name");
        }

        if(!isUserDeckNameAvailable(workplaceId, id, name)) {
            log.error("[Deck Exception]: Deck with name={} already exists", name);
            throw ExceptionHelper.getAlreadyExistsException(UserDeck.class, "name");
        }
    }

    public boolean isUserHasAccessToWorkplace(UUID userWorkplaceId, UUID workplaceId) {
        return userWorkplaceId.equals(workplaceId);
    }

    private boolean isUserDeckNameAvailable(UUID workplaceId, UUID deckId, String name) {
        UserDeck userDeck = userDeckRepository.findOneByWorkplaceIdAndName(workplaceId, name).orElse(null);
        if (Objects.isNull(userDeck)) {
            return true;
        }
        return Objects.equals(userDeck.getId(), deckId);
    }

}
