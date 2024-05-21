package com.emetaplus.admin.workplace.model;

import com.emetaplus.admin.payments.model.Order;
import com.emetaplus.admin.plans.model.Plan;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "workplaces")
@Getter
@Setter
@FieldNameConstants
public class Workplace {

    @Id
    @GeneratedValue
    private UUID id;
    private UUID workplaceId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDateTime createdDate;
    private WorkplaceStatus status;
    private LocalDateTime expiredAt;

    private Integer freeDecksUsed = 0;
    private Integer freeBoardsUsed = 0;

    @ManyToOne
    private Plan plan;
    private LocalDateTime planPaidUntil;

    @OneToMany(mappedBy = "workplace")
    private List<Order> orders;
}
