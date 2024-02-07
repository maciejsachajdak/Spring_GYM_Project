package com.gymapplication.user_panel_service.entityDbClubsAndPasses;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "passes")
public class Pass implements Serializable {

    @Serial
    private static final long serialVersionUID = 3495281077080266598L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "entry_number", nullable = false)
    private Integer entryNumber;

    @Column(name = "available_clubs_number",nullable = false)
    private Integer availableClubsNumber;

    @Column(nullable = false)
    private Double price;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Transient
    private String imgUrl;
}
