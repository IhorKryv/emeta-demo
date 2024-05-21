package com.emetaplus.workplace.builder.shortinfo.mapper;

import com.emetaplus.workplace.builder.banner.dto.BannerDTO;
import com.emetaplus.workplace.builder.banner.model.Banner;
import com.emetaplus.workplace.builder.shortinfo.dto.ShortInfoCardDTO;
import com.emetaplus.workplace.builder.shortinfo.dto.ShortInfoDTO;
import com.emetaplus.workplace.builder.shortinfo.model.ShortInfo;
import com.emetaplus.workplace.builder.shortinfo.model.ShortInfoCard;
import com.emetaplus.workplace.core.mapper.AbstractMapperFactory;
import com.emetaplus.workplace.core.mapper.MappingInstance;
import com.emetaplus.workplace.core.mapper.MappingInstancesBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Component
public class ShortInfoMapper extends AbstractMapperFactory {
    protected ShortInfoMapper(ModelMapper mapper) {
        super(mapper);
    }

    @Override
    protected void setupMappings(MappingInstancesBuilder builder) {
        MappingInstance<ShortInfo, ShortInfoDTO> infoInstance = new MappingInstance<>(ShortInfo.class, ShortInfoDTO.class);
        infoInstance.setPostMappingToDto(((info, infoDTO) -> {
            infoDTO.setProfileId(info.getProfile().getId().toString());
            infoDTO.setCards(infoDTO.getCards().stream().sorted(Comparator.comparingInt(ShortInfoCardDTO::getOrderValue)).toList());
        }));
        builder
                .add(infoInstance);
    }

    public ShortInfoDTO toDTO(ShortInfo info) {
        return getMapper(ShortInfo.class, ShortInfoDTO.class).toDTO(info);
    }
}
