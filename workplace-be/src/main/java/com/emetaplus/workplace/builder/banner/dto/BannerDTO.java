package com.emetaplus.workplace.builder.banner.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class BannerDTO {
    private String title;
    private String info;
    private String image;
    private String background;
    private String profileId;
}
