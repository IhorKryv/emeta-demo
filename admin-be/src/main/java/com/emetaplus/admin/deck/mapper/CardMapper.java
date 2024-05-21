package com.emetaplus.admin.deck.mapper;

import com.emetaplus.admin.core.dto.PageDTO;
import com.emetaplus.admin.core.mapper.AbstractMapperFactory;
import com.emetaplus.admin.core.mapper.MappingInstance;
import com.emetaplus.admin.core.mapper.MappingInstancesBuilder;
import com.emetaplus.admin.core.minio.MinioClientBuilder;
import com.emetaplus.admin.core.minio.MinioService;
import com.emetaplus.admin.deck.dto.CardDTO;
import com.emetaplus.admin.deck.dto.CardWithSizeDTO;
import com.emetaplus.admin.deck.model.Card;
import io.minio.MinioClient;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class CardMapper extends AbstractMapperFactory {
    private final MinioService minioService;

    protected CardMapper(ModelMapper mapper, MinioService minioService) {
        super(mapper);
        this.minioService = minioService;
    }

    @Override
    protected void setupMappings(MappingInstancesBuilder builder) {
        MappingInstance<Card, CardDTO> cardInstance = new MappingInstance<>(Card.class, CardDTO.class);
        MappingInstance<Card, CardWithSizeDTO> cardWithSizeInstance = new MappingInstance<>(Card.class, CardWithSizeDTO.class);
        cardInstance.setPostMappingToDto((card, cardDTO) -> {
            cardDTO.setUrl(
                    Objects.isNull(card.getId())
                            ? ""
                            : minioService.getCardImage(card.getDeckId().toString(), card.getId().toString(), card.getExtension())
            );
        });
        cardWithSizeInstance.setPostMappingToDto((card, cardDTO) -> {
            cardDTO.setUrl(
                    Objects.isNull(card.getId())
                            ? ""
                            : minioService.getCardImage(card.getDeckId().toString(), card.getId().toString(), card.getExtension())
            );
            cardDTO.setWidth(0);
            cardDTO.setHeight(0);
        });
        builder
                .add(cardInstance)
                .add(cardWithSizeInstance);
    }

    public Card toEntity(CardDTO dto) {
        return getMapper(Card.class, CardDTO.class).toEntity(dto);
    }

    public CardDTO toDTO(Card card) {
        return getMapper(Card.class, CardDTO.class).toDTO(card);
    }

    public PageDTO<CardDTO> toCardPageDTO(Page<Card> cards) {
        return getMapper(Card.class, CardDTO.class).toDTO(cards);
    }

    public List<CardDTO> toCardListDTO(List<Card> cards) {
        return getMapper(Card.class, CardDTO.class).toDTO(cards);
    }

    public List<CardWithSizeDTO> toCardWithSizeDTOList(List<Card> cards) {
        return getMapper(Card.class, CardWithSizeDTO.class).toDTO(cards);
    }
}
