package com.emetaplus.workplace.builder.shortinfo.mapper;

import com.emetaplus.workplace.builder.shortinfo.dto.ShortInfoCardDTO;
import com.emetaplus.workplace.builder.shortinfo.model.ShortInfoCard;
import com.emetaplus.workplace.core.mapper.AbstractMapperFactory;
import com.emetaplus.workplace.core.mapper.MappingInstance;
import com.emetaplus.workplace.core.mapper.MappingInstancesBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShortInfoCardMapper extends AbstractMapperFactory {
    protected ShortInfoCardMapper(ModelMapper mapper) {
        super(mapper);
    }

    @Override
    protected void setupMappings(MappingInstancesBuilder builder) {
        MappingInstance<ShortInfoCard, ShortInfoCardDTO> infoCardInstance = new MappingInstance<>(ShortInfoCard.class, ShortInfoCardDTO.class);
        builder
                .add(infoCardInstance);
    }

    public ShortInfoCardDTO toDTO(ShortInfoCard info) {
        return getMapper(ShortInfoCard.class, ShortInfoCardDTO.class).toDTO(info);
    }

    public List<ShortInfoCardDTO> toListDTO(List<ShortInfoCard> cards) {
        return getMapper(ShortInfoCard.class, ShortInfoCardDTO.class).toDtoList(cards);
    }

}
