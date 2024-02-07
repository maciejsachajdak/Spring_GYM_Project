package com.gymapplication.club_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gymapplication.club_service.entity.Club;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OpinionDto {

    private Long id;

    @NotEmpty(message = "This field cannot be empty")
    private String name;

    private Integer rate;

    private Timestamp date;

    @NotEmpty(message = "This field cannot be empty")
    private String content;
}
