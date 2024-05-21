package com.emetaplus.workplace.builder.contact.model;

import com.emetaplus.workplace.profile.model.Profile;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "profile_contacts")
public class ProfileContact {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "profile_id", unique = true, nullable = false)
    private Profile profile;

    @Column
    private String title;

    @Column
    private String shortText;

    @Column
    private String email;

    @Column
    private String phone;
}
