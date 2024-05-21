package com.emetaplus.workplace.client.model;

import com.emetaplus.workplace.session.model.Session;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "clients")
@FieldNameConstants
public class Client {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String firstName;
    private String lastName = "";
    private String email = "";
    private String phone = "";
    private boolean onSession = false;

    @Column(name = "workplace_id")
    private UUID workplaceId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false, insertable = false, updatable = false)
    private List<ClientRecord> records = new ArrayList<>();

}
