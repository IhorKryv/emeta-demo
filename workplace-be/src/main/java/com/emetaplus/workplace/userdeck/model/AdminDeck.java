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
import javax.persistence.Transient;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@Table(name = "admin_decks")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminDeck {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    @Column(name = "custom_name")
    private String customName;
    private String description;
    @Column(name = "custom_description")
    private String customDescription;
    @Column(name = "cards_count")
    private int cardsCount;
    @Column(name = "card_back")
    private String cardBack;
    @Column(name = "card_back_extension")
    private String cardBackExtension;
    @Transient
    private String cardBackUrl;
    @Column(name = "workplace_id")
    private UUID workplaceId;
    @Column(name = "admin_id")
    private UUID adminId;
}
