package com.emetaplus.workplace.userdeck.mapper;

import com.emetaplus.workplace.core.dto.PageDTO;
import com.emetaplus.workplace.core.mapper.AbstractMapperFactory;
import com.emetaplus.workplace.core.mapper.MappingInstance;
import com.emetaplus.workplace.core.mapper.MappingInstancesBuilder;
import com.emetaplus.workplace.core.minio.MinioService;
import com.emetaplus.workplace.security.jwt.UserUtils;
import com.emetaplus.workplace.userdeck.dto.CardDTO;
import com.emetaplus.workplace.userdeck.dto.CardWithSizeDTO;
import com.emetaplus.workplace.userdeck.model.Card;
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
        cardInstance.setPostMappingToDto((userCard, cardDTO) -> {
            cardDTO.setUrl(
                    Objects.isNull(userCard.getId())
                    ? ""
                    : minioService.getCardImage(
                            UserUtils.getCurrentUser().getWorkplaceId().toString(),
                            userCard.getDeckId().toString(),
                            userCard.getId().toString(),
                            userCard.getExtension()
                    )
            );
        });

        cardWithSizeInstance.setPostMappingToDto((userCard, cardDTO) -> {
            cardDTO.setUrl(
                    Objects.isNull(userCard.getId())
                            ? ""
                            : minioService.getCardImage(
                            UserUtils.getCurrentUser().getWorkplaceId().toString(),
                            userCard.getDeckId().toString(),
                            userCard.getId().toString(),
                            userCard.getExtension()
                    )
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

    public List<CardDTO> toCardDTOList(List<Card> cards) {
        return getMapper(Card.class, CardDTO.class).toDtoList(cards);
    }

    public List<CardWithSizeDTO> toCardWithSizeDTOList(List<Card> cards) {
        return getMapper(Card.class, CardWithSizeDTO.class).toDtoList(cards);
    }
}
