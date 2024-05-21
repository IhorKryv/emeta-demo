package com.emetaplus.workplace.userdeck.dto;

import lombok.Data;

@Data
public class CardWithSizeDTO extends CardDTO {
    private int width;
    private int height;
}
