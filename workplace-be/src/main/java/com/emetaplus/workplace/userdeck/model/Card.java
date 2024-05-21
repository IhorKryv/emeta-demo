package com.emetaplus.workplace.userdeck.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_cards")
@FieldNameConstants
public class Card {

    public Card(UUID deckId) {
        this.deckId = deckId;
    }

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "deck_id")
    private UUID deckId;

    private String extension;
}
