package com.emetaplus.workplace.client.mapper;

import com.emetaplus.workplace.client.dto.ClientDTO;
import com.emetaplus.workplace.client.model.Client;
import com.emetaplus.workplace.core.dto.PageDTO;
import com.emetaplus.workplace.core.mapper.AbstractMapperFactory;
import com.emetaplus.workplace.core.mapper.MappingInstance;
import com.emetaplus.workplace.core.mapper.MappingInstancesBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class ClientMapper extends AbstractMapperFactory {
    protected ClientMapper(ModelMapper mapper) {
        super(mapper);
    }

    @Override
    protected void setupMappings(MappingInstancesBuilder builder) {
        MappingInstance<Client, ClientDTO> clientInstance = new MappingInstance<>(Client.class, ClientDTO.class);
        builder
                .add(clientInstance);

    }

    public Client toEntity(ClientDTO dto) {
        return getMapper(Client.class, ClientDTO.class).toEntity(dto);
    }

    public ClientDTO toDTO(Client deck) {
        return getMapper(Client.class, ClientDTO.class).toDTO(deck);
    }

    public PageDTO<ClientDTO> toClientsPageDTO(Page<Client> clients) {
        return getMapper(Client.class, ClientDTO.class).toDTO(clients);
    }

    public Set<Client> toClientSet(Set<ClientDTO> clientDTOs) {
        return getMapper(Client.class, ClientDTO.class).toEntitySet(clientDTOs);
    }

    public Set<ClientDTO> toClientDTOSet(Set<Client> clients) {
        return getMapper(Client.class, ClientDTO.class).toDtoSet(clients);
    }
}
