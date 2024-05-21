package com.emetaplus.admin.deck.dto;

import lombok.Data;

@Data
public class CardWithSizeDTO extends CardDTO {
    private int width;
    private int height;
}
