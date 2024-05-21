package com.emetaplus.workplace.userdeck.mapper;

import com.emetaplus.workplace.core.mapper.AbstractMapperFactory;
import com.emetaplus.workplace.core.mapper.MappingInstance;
import com.emetaplus.workplace.core.mapper.MappingInstancesBuilder;
import com.emetaplus.workplace.core.minio.MinioService;
import com.emetaplus.workplace.userdeck.dto.AdminDeckDTO;
import com.emetaplus.workplace.userdeck.model.AdminDeck;
import com.emetaplus.workplace.userdeck.service.AdminDeckService;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class AdminDeckMapper extends AbstractMapperFactory {
    private final MinioService minioService;

    private final AdminDeckService adminDeckService;

    protected AdminDeckMapper(ModelMapper mapper, MinioService minioService, AdminDeckService adminDeckService) {
        super(mapper);
        this.minioService = minioService;
        this.adminDeckService = adminDeckService;
    }

    @Override
    protected void setupMappings(MappingInstancesBuilder builder) {
        MappingInstance<AdminDeck, AdminDeckDTO> adminDeckInstance = new MappingInstance<>(AdminDeck.class, AdminDeckDTO.class);
        adminDeckInstance.setPostMappingToDto((deck, deckDTO) -> {
            if (StringUtils.isBlank(deckDTO.getCardBackUrl())) {
                deckDTO.setCardBackUrl(
                        StringUtils.isBlank(deck.getCardBack())
                        ? ""
                        : minioService.getCardImageFromAdmin(deck.getAdminId().toString(), deck.getCardBack(), deck.getCardBackExtension()));
            }
            deckDTO.setDeckInCollection(adminDeckService.isDeckInCollection(deck.getAdminId()));
        });
        builder
                .add(adminDeckInstance);

    }

    public AdminDeck toEntity(AdminDeckDTO dto) {
        return getMapper(AdminDeck.class, AdminDeckDTO.class).toEntity(dto);
    }

    public AdminDeckDTO toDTO(AdminDeck deck) {
        return getMapper(AdminDeck.class, AdminDeckDTO.class).toDTO(deck);
    }

    public List<AdminDeckDTO> toDeckListDTO(List<AdminDeck> decks) {
        return getMapper(AdminDeck.class, AdminDeckDTO.class).toDtoList(decks);
    }
}
