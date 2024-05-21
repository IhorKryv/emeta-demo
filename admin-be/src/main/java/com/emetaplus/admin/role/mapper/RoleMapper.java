package com.emetaplus.admin.role.mapper;

import com.emetaplus.admin.core.dto.PageDTO;
import com.emetaplus.admin.core.mapper.AbstractMapperFactory;
import com.emetaplus.admin.core.mapper.MappingInstance;
import com.emetaplus.admin.core.mapper.MappingInstancesBuilder;
import com.emetaplus.admin.role.dto.RoleDTO;
import com.emetaplus.admin.role.model.Role;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper extends AbstractMapperFactory {

    protected RoleMapper(ModelMapper mapper) {
        super(mapper);
    }
    @Override
    protected void setupMappings(MappingInstancesBuilder builder) {
        MappingInstance<Role, RoleDTO> userInstance = new MappingInstance<>(Role.class, RoleDTO.class);
        builder
                .add(userInstance);
    }

    public Role toEntity(RoleDTO dto) {
        return getMapper(Role.class, RoleDTO.class).toEntity(dto);
    }

    public RoleDTO toDTO(Role role) {
        return getMapper(Role.class, RoleDTO.class).toDTO(role);
    }

    public PageDTO<RoleDTO> toRolePageDTO(Page<Role> roles) {
        return getMapper(Role.class, RoleDTO.class).toDTO(roles);
    }
}
