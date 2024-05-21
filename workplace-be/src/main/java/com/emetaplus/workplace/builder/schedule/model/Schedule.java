package com.emetaplus.workplace.builder.schedule.model;

import com.emetaplus.workplace.profile.model.Profile;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Map;
import java.util.UUID;

@Data
@Entity
@Table(name = "schedules")
public class Schedule {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "profile_id", unique = true, nullable = false)
    private Profile profile;

    @Column
    private String title;

    @Column
    private String shortText;

    @Column
    private String items;

}
