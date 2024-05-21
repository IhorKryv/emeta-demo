package com.emetaplus.workplace.client.model;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "clientfiles")
public class ClientFile {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "client_record_id")
    private UUID clientRecordId;

    @Column
    private String name;
    @Column
    private String extension;


}
