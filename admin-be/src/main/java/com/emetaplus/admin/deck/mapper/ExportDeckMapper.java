package com.emetaplus.admin.deck.mapper;

import com.emetaplus.admin.core.dto.PageDTO;
import com.emetaplus.admin.core.mapper.AbstractMapperFactory;
import com.emetaplus.admin.core.mapper.MappingInstance;
import com.emetaplus.admin.core.mapper.MappingInstancesBuilder;
import com.emetaplus.admin.core.minio.MinioService;
import com.emetaplus.admin.deck.dto.ExportDeckDTO;
import com.emetaplus.admin.deck.model.Deck;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ExportDeckMapper extends AbstractMapperFactory {

    private final MinioService minioService;

    protected ExportDeckMapper(ModelMapper mapper, MinioService minioService) {
        super(mapper);
        this.minioService = minioService;
    }

    @Override
    protected void setupMappings(MappingInstancesBuilder builder) {
        MappingInstance<Deck, ExportDeckDTO> exportDeckInstance = new MappingInstance<>(Deck.class, ExportDeckDTO.class);
        exportDeckInstance.setPostMappingToDto((deck, deckDTO) -> {
            final var cardBackUrl = Objects.isNull(deck.getCardBack())
                    ? ""
                    : minioService.getCardImage(deck.getId().toString(), deck.getCardBack(), deck.getCardBackExtension());
            deckDTO.setCardBackUrl(cardBackUrl);
            deckDTO.setAdminId(deck.getId());
        });
        builder
                .add(exportDeckInstance);
    }


    public ExportDeckDTO toDTO(Deck deck) {
        return getMapper(Deck.class, ExportDeckDTO.class).toDTO(deck);
    }

    public PageDTO<ExportDeckDTO> toDeckPageDTO(Page<Deck> decks) {
        return getMapper(Deck.class, ExportDeckDTO.class).toDTO(decks);
    }

    public List<ExportDeckDTO> toDeckDTOList(List<Deck> decks) {
        return getMapper(Deck.class, ExportDeckDTO.class).toDTO(decks);
    }
}
