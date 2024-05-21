package com.emetaplus.admin.workplace.dto;

import com.emetaplus.admin.core.dto.IdDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WorkplaceCreateDTO extends WorkplaceUpdateDTO {
    @NotNull
    private IdDTO plan;
}
