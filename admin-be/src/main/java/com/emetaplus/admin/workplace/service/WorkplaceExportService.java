package com.emetaplus.admin.workplace.service;

import com.emetaplus.admin.business.mac.model.MacSettings;
import com.emetaplus.admin.business.mac.repository.MacSettingsRepository;
import com.emetaplus.admin.core.exception.ExceptionHelper;
import com.emetaplus.admin.plans.model.Plan;
import com.emetaplus.admin.plans.repository.PlanRepository;
import com.emetaplus.admin.workplace.dto.WorkplaceExportDTO;
import com.emetaplus.admin.workplace.dto.WorkplaceExportSettingsDTO;
import com.emetaplus.admin.workplace.model.Workplace;
import com.emetaplus.admin.workplace.model.WorkplaceStatus;
import com.emetaplus.admin.workplace.repository.WorkplaceRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkplaceExportService {

    private final WorkplaceRepository workplaceRepository;
    private final PlanRepository planRepository;

    public void createWorkplace(WorkplaceExportDTO dto) {
        Workplace workplace = new Workplace();
        workplace.setWorkplaceId(dto.getWorkplaceId());
        workplace.setCreatedDate(LocalDateTime.now());
        workplace.setEmail(dto.getEmail());
        workplace.setFirstName(dto.getFirstName());
        workplace.setLastName(dto.getLastName());
        workplace.setFreeBoardsUsed(0);
        workplace.setFreeDecksUsed(0);
        workplace.setStatus(WorkplaceStatus.NEW);
        workplace.setPhone(dto.getPhone());
        workplace.setOrders(new ArrayList<>());
        Optional<Plan> defaultPlan = planRepository.findByDefaultPlanTrue();
        if (defaultPlan.isEmpty()) {
            throw ExceptionHelper.getNotFoundException(Plan.class);
        }
        workplace.setPlan(defaultPlan.get());
        workplace.setPlanPaidUntil(LocalDateTime.now().plusDays(5));
        workplace.setStatus(WorkplaceStatus.ACTIVATED);
        workplaceRepository.save(workplace);
    }

    public WorkplaceExportSettingsDTO getWorkplaceSettings(UUID workplaceId) {
        Workplace workplace = getWorkplaceBySharedId(workplaceId);
        WorkplaceExportSettingsDTO settingsDTO = new WorkplaceExportSettingsDTO();
        settingsDTO.setWorkplaceStatus(workplace.getStatus());
        settingsDTO.setAllowOwnDecks(workplace.getPlan().getMacSettings().getAllowOwnCards());
        settingsDTO.setAllowPersonalPage(workplace.getPlan().getMacSettings().getPersonalPage());
        settingsDTO.setFreeBoardsCount(workplace.getPlan().getMacSettings().getNumberOfFreeBoards());
        settingsDTO.setFreeDecksCount(workplace.getPlan().getMacSettings().getNumberOfFreeDecks());
        settingsDTO.setFreeBoardsUsed(workplace.getFreeBoardsUsed());
        settingsDTO.setFreeDecksUsed(workplace.getFreeDecksUsed());
        return settingsDTO;
    }

    public WorkplaceStatus getWorkplaceStatus(UUID workplaceId) {
        Workplace workplace = getWorkplaceBySharedId(workplaceId);
        return workplace.getStatus();
    }

    public void increaseUsedDecksCount(UUID workplaceId) {
        Workplace workplace = getWorkplaceBySharedId(workplaceId);
        workplace.setFreeDecksUsed(workplace.getFreeDecksUsed() + 1);
        workplaceRepository.save(workplace);
    }

    public void increaseUsedBoardsCount(UUID workplaceId) {
        Workplace workplace = getWorkplaceBySharedId(workplaceId);
        workplace.setFreeBoardsUsed(workplace.getFreeBoardsUsed() + 1);
        workplaceRepository.save(workplace);
    }

    private Workplace getWorkplaceBySharedId(UUID workplaceId) {
        return workplaceRepository.findOneByWorkplaceId(workplaceId).orElseThrow(() -> ExceptionHelper.getNotFoundException(Workplace.class));
    }
}
