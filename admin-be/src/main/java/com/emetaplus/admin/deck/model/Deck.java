package com.emetaplus.admin.deck.model;

import com.emetaplus.admin.category.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "decks")
@FieldNameConstants
public class Deck {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String description;
    @Column(name = "cards_count")
    private int cardsCount = 0;
    @Column(name = "card_back")
    private String cardBack;
    @Column(name = "card_back_extension")
    private String cardBackExtension;
    private boolean available = true;
    private boolean premium = false;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY)
    @JoinColumn(name = "deck_id", nullable = false, insertable = false, updatable = false)
    private List<Card> cardList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "decks_categories",
            joinColumns = @JoinColumn(name = "deck_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;
}
