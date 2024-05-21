package com.emetaplus.workplace.session.service;

import com.emetaplus.workplace.client.model.Client;
import com.emetaplus.workplace.core.exception.ExceptionHelper;
import com.emetaplus.workplace.core.query.OrderAttribute;
import com.emetaplus.workplace.core.query.PageableRequestQuery;
import com.emetaplus.workplace.core.utils.SpecificationUtils;
import com.emetaplus.workplace.email.model.EmailRequest;
import com.emetaplus.workplace.email.service.EmailService;
import com.emetaplus.workplace.security.jwt.UserUtils;
import com.emetaplus.workplace.session.model.Session;
import com.emetaplus.workplace.session.model.SessionStatus;
import com.emetaplus.workplace.session.repository.SessionRepository;
import com.emetaplus.workplace.userdeck.model.UserDeck;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final SessionValidator sessionValidator;

    private final EmailService emailService;

    public Session createSession(Session session) {
        sessionValidator.validateSessionForCreate(session);

        session.setCreatedAt(LocalDateTime.now().withNano(0));
        session.setUpdatedAt(session.getCreatedAt().withNano(0));
        session.setWorkplaceId(UserUtils.getCurrentUser().getWorkplaceId());
        session.setClients(new HashSet<>());
        session.setSessionStatus(SessionStatus.NEW);
        if (session.isInstant()) {
            session.setEndTime(session.getCreatedAt().plusMinutes(session.getSessionDuration()));
        } else {
            session.setEndTime(session.getStartTime().plusMinutes(session.getSessionDuration()));
        }
        return sessionRepository.save(session);

    }

    public Session addMembersToSession(UUID id, Set<Client> clients) {
        Session existingSession = getSessionById(id);
        existingSession.setClients(clients);
        Map<String, Object> data = Map.of(
                "sessionName", existingSession.getName(),
                "sessionDate", existingSession.getStartTime().toString(),
                "sessionURL", "https://session.emetaplus.com/" + existingSession.getId()

        );
        clients.stream().filter(client -> StringUtils.isNotBlank(client.getEmail())).forEach(client -> {
            String subject = "New EMeta Session has been created with your participation";
            String message = emailService.getContentFromTemplate(data, "client-added-to-session-template");
            EmailRequest emailRequest = new EmailRequest(
                    "no-reply@emetaplus.com",
                    client.getEmail(),
                    subject,
                    message
            );
            emailService.sendEmail(emailRequest);
        });
        existingSession.setUpdatedAt(LocalDateTime.now().withNano(0));
        return sessionRepository.save(existingSession);
    }

    public Session startSession(UUID id) {
        Session existingSession = getSessionById(id);
        if (existingSession.getSessionStatus() == SessionStatus.FINISHED) {
            throw ExceptionHelper.getInvalidFieldException(Session.class, "finished-status");
        }
        existingSession.setInProgress(true);
        existingSession.setSessionStatus(SessionStatus.IN_PROGRESS);
        existingSession.setStartTime(LocalDateTime.now().withNano(0));
        existingSession.setExpectedEndTime((existingSession.getStartTime().minusMinutes(existingSession.getSessionDuration())).withNano(0));
        existingSession.setHostFullName(
                String.format("%s %s", UserUtils.getCurrentUser().getFirstName(), UserUtils.getCurrentUser().getLastName())
        );

        return sessionRepository.save(existingSession);
    }

    public Session stopSession(UUID id) {
        Session existingSession = getSessionById(id);
        if (!existingSession.isInProgress()) {
            throw ExceptionHelper.getInvalidFieldException(Session.class, "in-progress");
        }
        existingSession.setEndTime(LocalDateTime.now().withNano(0));
        existingSession.setInProgress(false);
        existingSession.setSessionStatus(SessionStatus.FINISHED);
        existingSession.setExpectedEndTime(null);
        return sessionRepository.save(existingSession);
    }

    public Session updateSession(UUID id, Session session) {
        Session existingSession = getSessionById(id);
        sessionValidator.validateSessionForUpdate(existingSession.getId(), session);

        existingSession.setName(session.getName());
        existingSession.setDescription(session.getDescription());
        existingSession.setSessionDuration(session.getSessionDuration());
        existingSession.setStartTime(session.getStartTime().withNano(0));
        existingSession.setEndTime(session.getEndTime().withNano(0));
        existingSession.setUpdatedAt(LocalDateTime.now().withNano(0));
        return sessionRepository.save(existingSession);
    }

    public Session updateSessionOnStart(UUID id, String url) {
        Session existingSession = getSessionById(id);
        existingSession.setUrl(url);

        Map<String, Object> data = new HashMap<>();
        data.put("sessionName", existingSession.getName());
        data.put("sessionDate", existingSession.getStartTime().toString());
        data.put("sessionURL", existingSession.getUrl());

        existingSession.getClients().stream()
                .filter(client -> StringUtils.isNotBlank(client.getEmail()))
                .forEach(client -> {
                    data.put("clientName", client.getFirstName());
                    String subject = "The session with your participation has started!";
                    String message = emailService.getContentFromTemplate(data, "client-session-start-notify-template");
                    EmailRequest emailRequest = new EmailRequest(
                            "no-reply@emetaplus.com",
                            client.getEmail(),
                            subject,
                            message
                    );
                    emailService.sendEmail(emailRequest);
                });
        return sessionRepository.save(existingSession);
    }

    public Session getSession(UUID id) {
        return getSessionById(id);
    }

    public Page<Session> getAllAvailableSessions(PageableRequestQuery pageableRequestQuery) {
        pageableRequestQuery.setOrder(Map.of(
                1, new OrderAttribute(false, Session.Fields.updatedAt)
        ));
        Specification<Session> specification = getSpecification(UserUtils.getCurrentUser().getWorkplaceId(), pageableRequestQuery.getSearchText());
        return sessionRepository.findAll(specification, pageableRequestQuery.getPageable());
    }

    public Page<Session> getAllArchivedSessions(PageableRequestQuery pageableRequestQuery) {
        pageableRequestQuery.setOrder(Map.of(
                1, new OrderAttribute(false, Session.Fields.updatedAt)
        ));
        Specification<Session> specification = getArchivedSpecification(UserUtils.getCurrentUser().getWorkplaceId(), pageableRequestQuery.getSearchText());
        return sessionRepository.findAll(specification, pageableRequestQuery.getPageable());
    }

    public List<Session> getAllSessionsForCalendar() {
        return sessionRepository.findAllByWorkplaceId(UserUtils.getCurrentUser().getWorkplaceId());
    }

    public Session archiveSession(UUID id) {
        Session existingSession = getSessionById(id);
        existingSession.setSessionStatus(SessionStatus.ARCHIVED);
        return sessionRepository.save(existingSession);
    }

    public void deleteSession(UUID id) {
        Session existingSession = getSessionById(id);
        sessionRepository.delete(existingSession);
    }

    private Session getSessionById(UUID id) {
        Session session = sessionRepository.findById(id).orElseGet(() -> {
            log.error("[Session Error]: Session with id={} not found", id);
            throw ExceptionHelper.getNotFoundException(Session.class);
        });

        UserUtils.isUserHasAccess(session.getWorkplaceId());
        return session;
    }

    private Specification<Session> getSpecification(UUID workplaceId, String searchText) {
        Specification<Session> specification = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Session.Fields.workplaceId), workplaceId);
        specification
                .and((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get(Session.Fields.sessionStatus), SessionStatus.ARCHIVED.name()))
                .and(
                        SpecificationUtils.getSearchSpecification(searchText, root ->
                                Arrays.asList(root.get(Session.Fields.name), root.get(Session.Fields.description))
                        )
                );
        return specification;
    }

    private Specification<Session> getArchivedSpecification(UUID workplaceId, String searchText) {
        Specification<Session> specification = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Session.Fields.workplaceId), workplaceId);
        specification
                .and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Session.Fields.sessionStatus), SessionStatus.ARCHIVED.name()))
                .and(
                        SpecificationUtils.getSearchSpecification(searchText, root ->
                                Arrays.asList(root.get(Session.Fields.name), root.get(Session.Fields.description))
                        )
                );
        return specification;
    }
}
