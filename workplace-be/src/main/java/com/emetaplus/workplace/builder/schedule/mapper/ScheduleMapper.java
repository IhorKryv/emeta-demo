package com.emetaplus.workplace.builder.schedule.mapper;

import com.emetaplus.workplace.builder.schedule.dto.ScheduleDTO;
import com.emetaplus.workplace.builder.schedule.model.Schedule;
import com.emetaplus.workplace.core.mapper.AbstractMapperFactory;
import com.emetaplus.workplace.core.mapper.MappingInstance;
import com.emetaplus.workplace.core.mapper.MappingInstancesBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ScheduleMapper extends AbstractMapperFactory {
    protected ScheduleMapper(ModelMapper mapper) {
        super(mapper);
    }

    @Override
    protected void setupMappings(MappingInstancesBuilder builder) {
        MappingInstance<Schedule, ScheduleDTO> scheduleInstance = new MappingInstance<>(Schedule.class, ScheduleDTO.class);
        scheduleInstance.setPostMappingToDto((schedule, scheduleDTO) -> {
            scheduleDTO.setProfileId(schedule.getProfile().getId());
            try {
                scheduleDTO.setItems(new ObjectMapper().readValue(schedule.getItems(), new TypeReference<>() {}));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        builder.add(scheduleInstance);
    }

    public ScheduleDTO toDTO(Schedule schedule) {
        return getMapper(Schedule.class, ScheduleDTO.class).toDTO(schedule);
    }
}
