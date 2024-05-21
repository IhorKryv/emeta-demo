package com.emetaplus.admin.deck.mapper;

import com.emetaplus.admin.core.dto.PageDTO;
import com.emetaplus.admin.core.mapper.AbstractMapperFactory;
import com.emetaplus.admin.core.mapper.MappingInstance;
import com.emetaplus.admin.core.mapper.MappingInstancesBuilder;
import com.emetaplus.admin.core.minio.MinioService;
import com.emetaplus.admin.deck.dto.DeckDTO;
import com.emetaplus.admin.deck.model.Deck;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class DeckMapper extends AbstractMapperFactory {

    private final MinioService minioService;

    protected DeckMapper(ModelMapper mapper, MinioService minioService) {
        super(mapper);
        this.minioService = minioService;
    }

    @Override
    protected void setupMappings(MappingInstancesBuilder builder) {
        MappingInstance<Deck, DeckDTO> deckInstance = new MappingInstance<>(Deck.class, DeckDTO.class);
        deckInstance.setPostMappingToDto((deck, deckDTO) -> {
            final var cardBackUrl = Objects.isNull(deck.getCardBack())
                    ? ""
                    : minioService.getCardImage(deck.getId().toString(), deck.getCardBack(), deck.getCardBackExtension());
            deckDTO.setCardBackUrl(cardBackUrl);
        });
        builder
                .add(deckInstance);
    }

    public Deck toEntity(DeckDTO dto) {
        return getMapper(Deck.class, DeckDTO.class).toEntity(dto);
    }

    public DeckDTO toDTO(Deck deck) {
        return getMapper(Deck.class, DeckDTO.class).toDTO(deck);
    }

    public PageDTO<DeckDTO> toDeckPageDTO(Page<Deck> decks) {
        return getMapper(Deck.class, DeckDTO.class).toDTO(decks);
    }

}
