package com.emetaplus.workplace.userdeck.mapper;

import com.emetaplus.workplace.core.dto.PageDTO;
import com.emetaplus.workplace.core.mapper.AbstractMapperFactory;
import com.emetaplus.workplace.core.mapper.MappingInstance;
import com.emetaplus.workplace.core.mapper.MappingInstancesBuilder;
import com.emetaplus.workplace.core.minio.MinioService;
import com.emetaplus.workplace.userdeck.dto.UserDeckDTO;
import com.emetaplus.workplace.userdeck.model.UserDeck;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class UserDeckMapper extends AbstractMapperFactory {

    private final MinioService minioService;

    protected UserDeckMapper(ModelMapper mapper, MinioService minioService) {
        super(mapper);
        this.minioService = minioService;
    }

    @Override
    protected void setupMappings(MappingInstancesBuilder builder) {
        MappingInstance<UserDeck, UserDeckDTO> userDeckInstance = new MappingInstance<>(UserDeck.class, UserDeckDTO.class);
        userDeckInstance.setPostMappingToDto((deck, deckDTO) -> {
            final var cardBackUrl = Objects.isNull(deck.getCardBack())
                    ? ""
                    : minioService.getCardImage(
                    deck.getWorkplaceId().toString(),
                    deck.getId().toString(),
                    deck.getCardBack(),
                    deck.getCardBackExtension()
            );
            deckDTO.setCardBackUrl(cardBackUrl);
        });
        builder
                .add(userDeckInstance);

    }

    public UserDeck toEntity(UserDeckDTO dto) {
        return getMapper(UserDeck.class, UserDeckDTO.class).toEntity(dto);
    }

    public UserDeckDTO toDTO(UserDeck deck) {
        return getMapper(UserDeck.class, UserDeckDTO.class).toDTO(deck);
    }

    public List<UserDeckDTO> toListDTO(List<UserDeck> decks) {
        return getMapper(UserDeck.class, UserDeckDTO.class).toDTO(decks);
    }
}
