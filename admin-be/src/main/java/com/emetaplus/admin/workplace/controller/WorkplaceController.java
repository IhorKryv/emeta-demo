package com.emetaplus.admin.workplace.controller;

import com.emetaplus.admin.core.dto.IdDTO;
import com.emetaplus.admin.core.dto.PageDTO;
import com.emetaplus.admin.core.query.PageableRequestQuery;
import com.emetaplus.admin.plans.service.PlanService;
import com.emetaplus.admin.workplace.dto.*;
import com.emetaplus.admin.workplace.mapper.WorkplaceMapper;
import com.emetaplus.admin.workplace.model.Workplace;
import com.emetaplus.admin.workplace.service.WorkplaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/workplace")
@RequiredArgsConstructor
public class WorkplaceController {

    private final WorkplaceService workplaceService;
    private final PlanService planService;
    private final WorkplaceMapper workplaceMapper;

    @GetMapping
    public PageDTO<WorkplaceListDTO> getPaymentPlansPage(PageableRequestQuery pageableRequestQuery) {
        return workplaceMapper.toPageDTO(workplaceService.getAll(pageableRequestQuery));
    }

    @GetMapping("{id}")
    public WorkplaceDTO getPaymentPlansPage(@PathVariable UUID id) {
        return workplaceMapper.toDTO(workplaceService.getWorkplace(id));
    }

    @PostMapping
    public WorkplaceListDTO createNew(@RequestBody WorkplaceCreateDTO dto) {
        Workplace workplace = workplaceMapper.toEntity(dto);
        return workplaceMapper.toListDTO(workplaceService.createNew(workplace));
    }

    @PostMapping("temporary")
    public WorkplaceListDTO createTemporary(@RequestBody WorkplaceCreateDTO dto) {
        Workplace workplace = workplaceMapper.toEntity(dto);
        return workplaceMapper.toListDTO(workplaceService.createTemporary(workplace));
    }

    @PutMapping("{id}")
    public WorkplaceDTO updateWorkplaceData(@PathVariable UUID id, @RequestBody WorkplaceUpdateDTO dto) {
        Workplace workplace = workplaceMapper.toEntity(dto);
        return workplaceMapper.toDTO(workplaceService.updateUserData(id, workplace));
    }

    @PutMapping("{id}/plan")
    public WorkplaceDTO updateWorkplaceData(@PathVariable UUID id, @RequestBody IdDTO dto) {
        return workplaceMapper.toDTO(workplaceService.updatePaymentPlan(id, planService.getPlan(dto.toUUID())));
    }

    @PutMapping("{id}/status")
    public WorkplaceDTO updateWorkplaceStatus(@PathVariable UUID id, @RequestBody WorkplaceStatusUpdateDTO statusUpdateDTO) {
        return workplaceMapper.toDTO(workplaceService.updateWorkplaceStatus(id, statusUpdateDTO.getStatus()));
    }

    @DeleteMapping("{id}")
    public void disable(@PathVariable UUID id) {
        workplaceService.delete(id);
    }

    @DeleteMapping("{id}/permanently")
    public void deleteTemporary(@PathVariable UUID id) {
        workplaceService.permanentlyDelete(id);
    }
}
