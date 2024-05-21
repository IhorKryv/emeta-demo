package com.emetaplus.admin.workplace.service;

import com.emetaplus.admin.core.query.PageableRequestQuery;
import com.emetaplus.admin.plans.model.Plan;
import com.emetaplus.admin.workplace.model.Workplace;
import com.emetaplus.admin.workplace.model.WorkplaceStatus;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface WorkplaceService {

    Page<Workplace> getAll(PageableRequestQuery requestQuery);
    Workplace getWorkplace(UUID id);
    Workplace createNew(Workplace workplace);
    Workplace createTemporary(Workplace workplace);
    Workplace updateUserData(UUID id, Workplace workplace);
    Workplace updatePaymentPlan(UUID id, Plan plan);
    Workplace updateWorkplaceStatus(UUID id, WorkplaceStatus status);
    void activate(UUID id);
    void delete(UUID id);
    void suspend(UUID id);
    void permanentlyDelete(UUID id);

}
