package com.emetaplus.admin.deck.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "cards")
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

    @Column
    private String extension;
}
