package com.emetaplus.workplace.client.model;

import lombok.Data;
import lombok.Generated;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "clientrecords")
public class ClientRecord {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "client_id")
    private UUID clientId;

    @Column
    private String title;
    @Column
    private String content;
    @Column
    private LocalDateTime sessionDate;
    @Column
    private LocalDateTime createdDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_record_id", nullable = false, insertable = false, updatable = false)
    private List<ClientFile> files = new ArrayList<>();
}
