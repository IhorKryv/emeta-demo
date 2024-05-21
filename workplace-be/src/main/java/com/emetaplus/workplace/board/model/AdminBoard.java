package com.emetaplus.workplace.board.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@Table(name = "admin_boards")
public class AdminBoard {

    @Id
    @GeneratedValue
    private UUID id;
    private UUID adminId;
    private String name;
    @Column(name = "custom_name")
    private String customName;
    private String description;
    @Column(name = "custom_description")
    private String customDescription;
    private String image;
    @Transient
    private String imageUrl;
    @Column(name = "workplace_id")
    private UUID workplaceId;
}
