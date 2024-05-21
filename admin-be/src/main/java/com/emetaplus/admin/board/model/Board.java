package com.emetaplus.admin.board.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@FieldNameConstants
@Table(name = "boards")
public class Board {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String description;
    private String image;
}
