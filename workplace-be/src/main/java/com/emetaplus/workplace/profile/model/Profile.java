package com.emetaplus.workplace.profile.model;


import com.emetaplus.workplace.user.model.User;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue
    @Column(name = "profile_id")
    private UUID id;

    @Column
    private String url;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;
}
