package com.emetaplus.workplace.builder.shortinfo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "short_info_cards")
public class ShortInfoCard {
    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private String icon;

    @Column
    private String title;

    @Column
    private String text;

    @Column
    private int orderValue;

    @Column(name = "short_info_id")
    private UUID shortInfoId;


}
