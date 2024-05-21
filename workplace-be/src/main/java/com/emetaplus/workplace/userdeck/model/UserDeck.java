package com.emetaplus.workplace.userdeck.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@Table(name = "user_decks")
public class UserDeck {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String description;
    @Column(name = "cards_count")
    private int cardsCount;
    @Column(name = "card_back")
    private String cardBack;
    @Column(name = "card_back_extension")
    private String cardBackExtension;
    private boolean available = false;

    @Column(name = "workplace_id")
    private UUID workplaceId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY)
    @JoinColumn(name = "deck_id", nullable = false, insertable = false, updatable = false)
    private List<Card> cardList;

}
