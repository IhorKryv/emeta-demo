package com.emetaplus.workplace.session.mapper;

import com.emetaplus.workplace.client.dto.ClientDTO;
import com.emetaplus.workplace.client.model.Client;
import com.emetaplus.workplace.core.dto.PageDTO;
import com.emetaplus.workplace.core.mapper.AbstractMapperFactory;
import com.emetaplus.workplace.core.mapper.MappingInstance;
import com.emetaplus.workplace.core.mapper.MappingInstancesBuilder;
import com.emetaplus.workplace.session.dto.SessionCalendarItemDTO;
import com.emetaplus.workplace.session.dto.SessionDTO;
import com.emetaplus.workplace.session.model.Session;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class SessionCalendarMapper extends AbstractMapperFactory {

    protected SessionCalendarMapper(ModelMapper mapper) {
        super(mapper);
    }

    @Override
    protected void setupMappings(MappingInstancesBuilder builder) {
        MappingInstance<Session, SessionCalendarItemDTO> sessionInstance = new MappingInstance<>(Session.class, SessionCalendarItemDTO.class);
        MappingInstance<LocalDateTime, String> timeMappingInstance = new MappingInstance<>(LocalDateTime.class, String.class);
        MappingInstance<Client, ClientDTO> clientInstance = new MappingInstance<>(Client.class, ClientDTO.class);
        timeMappingInstance.setMappingToDto(localDateTime -> Objects.nonNull(localDateTime)
                ? String.format("%s:%s",
                localDateTime.getHour() > 10
                    ? localDateTime.getHour()
                    : "0" + localDateTime.getHour(),
                localDateTime.getMinute() > 10
                    ? localDateTime.getMinute()
                    : "0" + localDateTime.getMinute())
                : "");
        sessionInstance.setPostMappingToDto((session, sessionCalendarItemDTO) -> {
            sessionCalendarItemDTO.setDate(session.getStartTime().toLocalDate());
        });
        builder
                .add(sessionInstance)
                .add(clientInstance)
                .add(timeMappingInstance);

    }

    public Session toEntity(SessionCalendarItemDTO dto) {
        return getMapper(Session.class, SessionCalendarItemDTO.class).toEntity(dto);
    }

    public SessionCalendarItemDTO toDTO(Session session) {
        return getMapper(Session.class, SessionCalendarItemDTO.class).toDTO(session);
    }

    public List<SessionCalendarItemDTO> toDTOList(List<Session> sessionList) {
        return getMapper(Session.class, SessionCalendarItemDTO.class).toDtoList(sessionList);
    }
}
