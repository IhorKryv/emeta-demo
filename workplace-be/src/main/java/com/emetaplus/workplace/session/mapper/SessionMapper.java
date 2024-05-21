package com.emetaplus.workplace.session.mapper;

import com.emetaplus.workplace.client.dto.ClientDTO;
import com.emetaplus.workplace.client.model.Client;
import com.emetaplus.workplace.core.dto.PageDTO;
import com.emetaplus.workplace.core.mapper.AbstractMapperFactory;
import com.emetaplus.workplace.core.mapper.MappingInstance;
import com.emetaplus.workplace.core.mapper.MappingInstancesBuilder;
import com.emetaplus.workplace.session.dto.SessionDTO;
import com.emetaplus.workplace.session.model.Session;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class SessionMapper extends AbstractMapperFactory {

    protected SessionMapper(ModelMapper mapper) {
        super(mapper);
    }

    @Override
    protected void setupMappings(MappingInstancesBuilder builder) {
        MappingInstance<Session, SessionDTO> sessionInstance = new MappingInstance<>(Session.class, SessionDTO.class);
        MappingInstance<Client, ClientDTO> clientInstance = new MappingInstance<>(Client.class, ClientDTO.class);
        builder
                .add(sessionInstance)
                .add(clientInstance);

    }

    public Session toEntity(SessionDTO dto) {
        return getMapper(Session.class, SessionDTO.class).toEntity(dto);
    }

    public SessionDTO toDTO(Session session) {
        return getMapper(Session.class, SessionDTO.class).toDTO(session);
    }

    public PageDTO<SessionDTO> toSessionPageDTO(Page<Session> sessions) {
        return getMapper(Session.class, SessionDTO.class).toDTO(sessions);
    }
}
