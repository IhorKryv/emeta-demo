package com.emetaplus.workplace.builder.shortinfo.dto;

import lombok.Data;

import java.util.List;

@Data
public class ShortInfoDTO {
    private String title;
    private String description;
    private List<ShortInfoCardDTO> cards;
    private String profileId;
}
