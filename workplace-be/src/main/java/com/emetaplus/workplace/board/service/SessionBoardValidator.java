package com.emetaplus.workplace.board.service;

import com.emetaplus.workplace.board.model.SessionBoard;
import com.emetaplus.workplace.board.repository.SessionBoardRepository;
import com.emetaplus.workplace.core.exception.ExceptionHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SessionBoardValidator {

    private final SessionBoardRepository sessionBoardRepository;

    public void validateSessionBoardForCreate(SessionBoard sessionBoard) {
        validateSessionBoardName(sessionBoard.getName(), null, "create");
    }

    public void validateSessionBoardForUpdate(UUID id, SessionBoard sessionBoard) {
        validateSessionBoardName(sessionBoard.getName(), id, "update");
    }

    private void validateSessionBoardName(String name, UUID id, String method) {
        if (StringUtils.isBlank(name)) {
            log.error("[Session Board Exception]: Can't {} a session board without name", method);
            throw ExceptionHelper.getInvalidFieldException(SessionBoard.class, "name");
        }
        SessionBoard sessionBoard = sessionBoardRepository.findOneByName(name).orElse(null);
        if (Objects.nonNull(sessionBoard) && !sessionBoard.getId().equals(id)) {
            log.error("[Session Board Exception]: Session board with name={} already exists", name);
            throw ExceptionHelper.getAlreadyExistsException(SessionBoard.class, "name");
        }
    }


}
