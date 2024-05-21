package com.emetaplus.workplace.builder.banner.model;

import com.emetaplus.workplace.profile.model.Profile;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "banners")
public class Banner {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "profile_id", unique = true, nullable = false)
    private Profile profile;

    @Column
    private String title;

    @Column
    private String info;

    @Column
    private String image;

    @Column
    private String background;
}
