package com.emetaplus.admin.workplace.mapper;

import com.emetaplus.admin.core.dto.PageDTO;
import com.emetaplus.admin.core.mapper.AbstractMapperFactory;
import com.emetaplus.admin.core.mapper.MappingInstance;
import com.emetaplus.admin.core.mapper.MappingInstancesBuilder;
import com.emetaplus.admin.workplace.dto.*;
import com.emetaplus.admin.workplace.model.Workplace;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class WorkplaceMapper extends AbstractMapperFactory {

    protected WorkplaceMapper(ModelMapper mapper) {
        super(mapper);
    }

    @Override
    protected void setupMappings(MappingInstancesBuilder builder) {
        MappingInstance<Workplace, WorkplaceCreateDTO> workplaceCreateInstance = new MappingInstance<>(Workplace.class, WorkplaceCreateDTO.class);
        MappingInstance<Workplace, WorkplaceUpdateDTO> workplaceUpdateInstance = new MappingInstance<>(Workplace.class, WorkplaceUpdateDTO.class);
        MappingInstance<Workplace, WorkplaceListDTO> workplaceListInstance = new MappingInstance<>(Workplace.class, WorkplaceListDTO.class);
        MappingInstance<Workplace, WorkplaceInfoDTO> workplaceInfoInstance = new MappingInstance<>(Workplace.class, WorkplaceInfoDTO.class);
        MappingInstance<Workplace, WorkplaceDTO> workplaceInstance = new MappingInstance<>(Workplace.class, WorkplaceDTO.class);
        builder
                .add(workplaceCreateInstance)
                .add(workplaceUpdateInstance)
                .add(workplaceListInstance)
                .add(workplaceInfoInstance)
                .add(workplaceInstance);
    }

    public Workplace toEntity(WorkplaceCreateDTO workplaceCreateDTO) {
        return getMapper(Workplace.class, WorkplaceCreateDTO.class).toEntity(workplaceCreateDTO);
    }

    public Workplace toEntity(WorkplaceUpdateDTO workplaceUpdateDTO) {
        return getMapper(Workplace.class, WorkplaceUpdateDTO.class).toEntity(workplaceUpdateDTO);
    }

    public WorkplaceListDTO toListDTO(Workplace workplace) {
        return getMapper(Workplace.class, WorkplaceListDTO.class).toDTO(workplace);
    }

    public PageDTO<WorkplaceListDTO> toPageDTO(Page<Workplace> workplace) {
        return getMapper(Workplace.class, WorkplaceListDTO.class).toDTO(workplace);
    }

    public WorkplaceDTO toDTO(Workplace workplace) {
        return getMapper(Workplace.class, WorkplaceDTO.class).toDTO(workplace);
    }
}
