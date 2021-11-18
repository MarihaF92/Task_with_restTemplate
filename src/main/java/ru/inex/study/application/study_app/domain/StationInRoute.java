package ru.inex.study.application.study_app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StationInRoute {

    LocalDateTime arvTime;
    LocalDateTime arvTimeMSK;

    LocalDateTime depTime;
    LocalDateTime depTimeMSK;

    Stops stantion;

}
