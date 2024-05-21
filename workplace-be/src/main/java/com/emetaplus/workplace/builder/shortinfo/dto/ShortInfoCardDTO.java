package com.emetaplus.workplace.builder.shortinfo.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ShortInfoCardDTO {
    private UUID id;
    private String icon;
    private String title;
    private String text;
    private int orderValue;
}
