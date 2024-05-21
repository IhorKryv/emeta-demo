package com.emetaplus.workplace.builder.decks.model;

import com.emetaplus.workplace.profile.model.Profile;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "profile_decks")
public class ProfileDeck {

    @Id
    @GeneratedValue
    @Column(name = "profile_deck_id")
    private UUID id;

    @Column
    private String title;

    @Column
    private String info;

    @OneToOne
    @JoinColumn(name = "profile_id", unique = true, nullable = false)
    private Profile profile;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_deck_id", nullable = false, insertable = false, updatable = false)
    private List<ProfileDeckItem> decks;

}
