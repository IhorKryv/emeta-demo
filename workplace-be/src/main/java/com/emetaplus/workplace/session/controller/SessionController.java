package com.emetaplus.workplace.session.controller;

import com.emetaplus.workplace.client.dto.ClientDTO;
import com.emetaplus.workplace.client.mapper.ClientMapper;
import com.emetaplus.workplace.core.dto.PageDTO;
import com.emetaplus.workplace.core.query.PageableRequestQuery;
import com.emetaplus.workplace.session.dto.SessionCalendarItemDTO;
import com.emetaplus.workplace.session.dto.SessionDTO;
import com.emetaplus.workplace.session.dto.SessionStartDTO;
import com.emetaplus.workplace.session.mapper.SessionCalendarMapper;
import com.emetaplus.workplace.session.mapper.SessionMapper;
import com.emetaplus.workplace.session.model.SessionStatus;
import com.emetaplus.workplace.session.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/session")
@RequiredArgsConstructor
public class SessionController {

    private static final String CREATE_SESSION_PATH = "create";
    private static final String ADD_CLIENTS_TO_SESSION_PATH = "{id}/clients/add";
    private static final String UPDATE_SESSION_PATH = "update/{id}";
    private static final String UPDATE_SESSION_URL = "update/{id}/url";
    private static final String GET_SESSION_PATH = "get/{id}";
    private static final String GET_ALL_SESSIONS_PATH = "get/all";
    private static final String GET_ARCHIVED_SESSIONS_PATH = "get/archived";

    private static final String GET_ALL_FOR_CALENDAR_PATH = "get/calendar";
    private static final String START_SESSION_PATH = "start/{id}";
    private static final String STOP_SESSION_PATH = "stop/{id}";
    private static final String ARCHIVE_SESSION_PATH = "archive/{id}";
    private static final String DELETE_SESSION_PATH = "delete/{id}";
    private static final String SESSION_STATUSES_PATH = "get/statuses";

    private final SessionService sessionService;
    private final SessionMapper mapper;
    private final SessionCalendarMapper calendarMapper;
    private final ClientMapper clientMapper;

    @PostMapping(CREATE_SESSION_PATH)
    public SessionDTO createSession(@RequestBody SessionDTO dto) {
        return mapper.toDTO(sessionService.createSession(mapper.toEntity(dto)));
    }

    @PostMapping(ADD_CLIENTS_TO_SESSION_PATH)
    public SessionDTO addClientsToSession(@PathVariable UUID id, @RequestBody Set<ClientDTO> clientDTOs) {
        return mapper.toDTO(sessionService.addMembersToSession(id, clientMapper.toClientSet(clientDTOs)));
    }

    @PutMapping(UPDATE_SESSION_PATH)
    public SessionDTO updateSession(@PathVariable UUID id, @RequestBody SessionDTO dto) {
        return mapper.toDTO(sessionService.updateSession(id, mapper.toEntity(dto)));
    }

    @PutMapping(UPDATE_SESSION_URL)
    public SessionDTO updateSessionOnStart(@PathVariable UUID id, @RequestBody SessionStartDTO dto) {
        return mapper.toDTO(sessionService.updateSessionOnStart(id, dto.getUrl()));
    }

    @GetMapping(GET_SESSION_PATH)
    public SessionDTO getSession(@PathVariable UUID id) {
        return mapper.toDTO(sessionService.getSession(id));
    }

    @GetMapping(GET_ALL_FOR_CALENDAR_PATH)
    public List<SessionCalendarItemDTO> getAllSessionsForCalendar() {
        return calendarMapper.toDTOList(sessionService.getAllSessionsForCalendar());
    }

    @GetMapping(GET_ALL_SESSIONS_PATH)
    public PageDTO<SessionDTO> getAllSessions(PageableRequestQuery pageableRequestQuery) {
        return mapper.toSessionPageDTO(sessionService.getAllAvailableSessions(pageableRequestQuery));
    }

    @GetMapping(GET_ARCHIVED_SESSIONS_PATH)
    public PageDTO<SessionDTO> getAllArchivedSessions(PageableRequestQuery pageableRequestQuery) {
        return mapper.toSessionPageDTO(sessionService.getAllArchivedSessions(pageableRequestQuery));
    }

    @GetMapping(START_SESSION_PATH)
    public SessionDTO startSession(@PathVariable UUID id) {
        return mapper.toDTO(sessionService.startSession(id));
    }

    @GetMapping(STOP_SESSION_PATH)
    public SessionDTO stopSession(@PathVariable UUID id) {
        return mapper.toDTO(sessionService.stopSession(id));
    }

    @GetMapping(SESSION_STATUSES_PATH)
    public List<SessionStatus> getAllSessionStatuses() {
        return Arrays.stream(SessionStatus.values()).toList();
    }

    @DeleteMapping(ARCHIVE_SESSION_PATH)
    public SessionDTO archiveSession(@PathVariable UUID id) {
        return mapper.toDTO(sessionService.archiveSession(id));
    }

    @DeleteMapping(DELETE_SESSION_PATH)
    public void deleteSession(@PathVariable UUID id) {
        sessionService.deleteSession(id);
    }

}
