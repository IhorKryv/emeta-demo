package com.emetaplus.admin.deck.dto;

import com.emetaplus.admin.category.dto.CategoryDTO;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class DeckDTO {
    private UUID id;
    private String name;
    private String description;
    private String cardBack;
    private String cardBackUrl;
    private int cardsCount;
    private boolean available;
    private boolean premium;
    private List<CategoryDTO> categories;
}
