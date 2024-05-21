package com.emetaplus.admin.category.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@Table(name = "categories")
@FieldNameConstants
public class Category {

    public Category(UUID id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String description;
    private String identifier;
}
