package com.emetaplus.workplace.builder.contact.mapper;

import com.emetaplus.workplace.builder.contact.dto.ProfileContactDTO;
import com.emetaplus.workplace.builder.contact.model.ProfileContact;
import com.emetaplus.workplace.core.mapper.AbstractMapperFactory;
import com.emetaplus.workplace.core.mapper.MappingInstance;
import com.emetaplus.workplace.core.mapper.MappingInstancesBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProfileContactMapper extends AbstractMapperFactory {
    protected ProfileContactMapper(ModelMapper mapper) {
        super(mapper);
    }

    @Override
    protected void setupMappings(MappingInstancesBuilder builder) {
        MappingInstance<ProfileContact, ProfileContactDTO> profileContactInstance =
                new MappingInstance<>(ProfileContact.class, ProfileContactDTO.class);
        profileContactInstance.setPostMappingToDto((profileContact, profileContactDTO) -> {
            profileContactDTO.setProfileId(profileContact.getProfile().getId());
        });
        builder
                .add(profileContactInstance);
    }

    public ProfileContactDTO toDTO(ProfileContact profileContact) {
        return getMapper(ProfileContact.class, ProfileContactDTO.class).toDTO(profileContact);
    }
}
