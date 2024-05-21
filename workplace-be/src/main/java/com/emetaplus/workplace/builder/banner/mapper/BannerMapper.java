package com.emetaplus.workplace.builder.banner.mapper;

import com.emetaplus.workplace.builder.banner.dto.BannerDTO;
import com.emetaplus.workplace.builder.banner.model.Banner;
import com.emetaplus.workplace.core.mapper.AbstractMapperFactory;
import com.emetaplus.workplace.core.mapper.MappingInstance;
import com.emetaplus.workplace.core.mapper.MappingInstancesBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class BannerMapper extends AbstractMapperFactory {
    protected BannerMapper(ModelMapper mapper) {
        super(mapper);
    }

    @Override
    protected void setupMappings(MappingInstancesBuilder builder) {
        MappingInstance<Banner, BannerDTO> bannerInstance = new MappingInstance<>(Banner.class, BannerDTO.class);
        bannerInstance.setPostMappingToDto(((banner, bannerDTO) -> {
            bannerDTO.setProfileId(banner.getProfile().getId().toString());
        }));
        builder
                .add(bannerInstance);
    }

    public BannerDTO toDTO(Banner banner) {
        return getMapper(Banner.class, BannerDTO.class).toDTO(banner);
    }
}
