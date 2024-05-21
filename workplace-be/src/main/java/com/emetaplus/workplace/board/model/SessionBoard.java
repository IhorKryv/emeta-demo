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
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@Table(name = "session_boards")
public class SessionBoard {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String description;
    private String image;
    @Column(name = "image_extension")
    private String imageExtension;
    @Column(name = "workplace_id")
    private UUID workplaceId;
}
