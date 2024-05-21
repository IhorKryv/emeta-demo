package com.emetaplus.admin.workplace.dto;

import com.emetaplus.admin.plans.dto.PlanInfoDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkplaceDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private PlanInfoDTO plan;
    private String status;
    private LocalDateTime createdDate;
    private LocalDateTime expiredAt;
    private LocalDateTime planPaidUntil;

}
