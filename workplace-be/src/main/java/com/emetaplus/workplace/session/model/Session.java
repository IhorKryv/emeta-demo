package com.emetaplus.workplace.session.model;


import com.emetaplus.workplace.client.model.Client;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "sessions")
@FieldNameConstants
public class Session {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    //New class or just minutes ???
    private Long sessionDuration;
    private boolean inProgress = false;
    private String url;

    @Transient
    private LocalDateTime expectedEndTime;

    @Transient
    private String hostFullName;

    @Transient
    private boolean instant;

    @Enumerated(EnumType.STRING)
    @Column(name = "session_status")
    private SessionStatus sessionStatus;

    @Column(name = "workplace_id")
    private UUID workplaceId;

    @ManyToMany
    private Set<Client> clients;


}
