package com.emetaplus.admin.workplace.service;

import com.emetaplus.admin.core.exception.ExceptionHelper;
import com.emetaplus.admin.plans.model.Plan;
import com.emetaplus.admin.plans.repository.PlanRepository;
import com.emetaplus.admin.workplace.model.Workplace;
import com.emetaplus.admin.workplace.repository.WorkplaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkplaceValidators {

    private static final String CREATE_METHOD = "create";
    private static final String UPDATE_METHOD = "update";
    private final WorkplaceRepository workplaceRepository;
    private final PlanRepository planRepository;

    public void validateWorkplaceCreation(Workplace workplace) {
        validateName(workplace.getFirstName(), "FIRST", CREATE_METHOD);
        validateName(workplace.getLastName(), "LAST", CREATE_METHOD);
        validateEmail(null, workplace.getEmail(), CREATE_METHOD);
        validatePlan(workplace.getPlan(), CREATE_METHOD);
    }

    public void validateWorkplaceDataUpdate(Workplace workplace) {
        validateName(workplace.getFirstName(), "FIRST", UPDATE_METHOD);
        validateName(workplace.getLastName(), "LAST", UPDATE_METHOD);
        validateEmail(workplace.getId(), workplace.getEmail(), UPDATE_METHOD);
    }

    public void validateWorkplacePlanUpdate(Workplace workplace) {
       validatePlan(workplace.getPlan(), UPDATE_METHOD);
    }

    private void validateName(String name, String key, String method) {
        if (Objects.isNull(name) || name.isBlank()) {
            log.error("[Workplace Exception]: Can't {} workplace without {} name", method, key);
            throw ExceptionHelper.getInvalidFieldException(Workplace.class, key.toLowerCase());
        }
    }

    private void validateEmail(UUID existingId, String email, String method) {
        if (Objects.isNull(email) || email.isBlank()) {
            log.error("[Workplace Exception]: Can't {} workplace without email", method);
            throw ExceptionHelper.getInvalidFieldException(Workplace.class, "email".toLowerCase());
        }
        if (workplaceRepository.existsByEmailIgnoreCase(email) && !workplaceRepository.existsByEmailIgnoreCaseAndId(email, existingId)) {
            log.error("[Workplace Exception]: Can't {} workplace email is already used", method);
            throw ExceptionHelper.getAlreadyExistsException(Workplace.class, "email");
        }
    }

    private void validatePlan(Plan plan, String method) {
        if (Objects.isNull(plan) || Objects.isNull(plan.getId())) {
            log.error("[Workplace Exception]: Can't {} workplace without plan", method);
            throw ExceptionHelper.getInvalidFieldException(Workplace.class, "plan".toLowerCase());
        }
        if (!planRepository.existsByIdAndEnabled(plan.getId(), true)) {
            log.error("[Workplace Exception]: Can't {} workplace without plan", method);
            throw ExceptionHelper.getInvalidFieldException(Workplace.class, "plan".toLowerCase());
        }
    }
}
