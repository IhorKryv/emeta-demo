package com.emetaplus.workplace.builder.decks.model;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "profile_deck_items")
public class ProfileDeckItem {

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private String image;

    @Column
    private String title;

    @Column
    private String info;

    @Column(name = "profile_deck_id")
    private UUID profileDeckId;

    @Column
    private int orderValue;
}
