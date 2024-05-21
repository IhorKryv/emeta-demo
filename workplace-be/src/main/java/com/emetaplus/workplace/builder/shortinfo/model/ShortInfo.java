package com.emetaplus.workplace.builder.shortinfo.model;

import com.emetaplus.workplace.profile.model.Profile;
import com.emetaplus.workplace.userdeck.model.Card;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "short_infos")
public class ShortInfo {

    @Id
    @GeneratedValue
    @Column(name = "short_info_id")
    private UUID id;

    @Column
    private String title;

    @Column
    private String description;

    @OneToOne
    @JoinColumn(name = "profile_id", unique = true, nullable = false)
    private Profile profile;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.EAGER)
    @JoinColumn(name = "short_info_id", nullable = false, insertable = false, updatable = false)
    private List<ShortInfoCard> cards;


}
