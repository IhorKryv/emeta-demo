package com.emetaplus.workplace.session.service;

import com.emetaplus.workplace.core.exception.ExceptionHelper;
import com.emetaplus.workplace.security.jwt.UserUtils;
import com.emetaplus.workplace.session.model.Session;
import com.emetaplus.workplace.session.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SessionValidator {

    private final SessionRepository sessionRepository;

    public void validateSessionForCreate(Session session) {
        validateName(null, session.getName(), "create");
        validateScheduleData(session, "create");
    }

    public void validateSessionForUpdate(UUID id, Session session) {
        validateName(id, session.getName(), "update");
        validateScheduleData(session, "update");
    }

    private void validateName(UUID id, String name, String method) {
        if (StringUtils.isBlank(name)) {
            log.error("[Session Exception]: Can't {} a session without name", method);
            throw ExceptionHelper.getInvalidFieldException(Session.class, "name");
        }
    }

    private void validateScheduleData(Session session, String method) {
        if (Objects.isNull(session.getStartTime())) {
            log.error("[Session Exception]: Can't {} a session without start time", method);
            throw ExceptionHelper.getInvalidFieldException(Session.class, "start-time");
        }

        if (Objects.isNull(session.getSessionDuration())) {
            log.error("[Session Exception]: Can't {} a session without duration", method);
            throw ExceptionHelper.getInvalidFieldException(Session.class, "session-duration");
        }
    }
}
