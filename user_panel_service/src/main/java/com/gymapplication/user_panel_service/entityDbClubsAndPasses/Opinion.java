package com.gymapplication.user_panel_service.entityDbClubsAndPasses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="opinions")
public class Opinion
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String name;

    @Column(nullable=false)
    private Integer rate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Warsaw")
    private Timestamp date;

    @Column(nullable=false)
    private String content;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "CLUB_ID")
    private Club club;
}