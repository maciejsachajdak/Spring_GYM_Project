package com.gymapplication.club_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "clubs")
public class Club implements Serializable {

    @Serial
    private static final long serialVersionUID = -2215532452848138754L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(name="open_hours",nullable = false)
    private String openHours;

    @Column(name="average_rate",nullable = false)
    private Double averageRate;

    @OneToMany(mappedBy = "club")
    private List<Opinion> opinions = new ArrayList<>();

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Transient
    private String imgUrl;
}
