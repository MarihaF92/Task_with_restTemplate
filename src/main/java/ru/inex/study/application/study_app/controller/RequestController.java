package ru.inex.study.application.study_app.controller;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.inex.study.application.study_app.service.RequestService;


import java.time.LocalDate;


@Controller
@AllArgsConstructor
public class RequestController {

    private final RequestService requestService;


    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @RequestMapping("/request")
    public void anyAction(@RequestParam(value = "trainNumber") String trainNumber,
                                         @RequestParam(value = "departureDate") @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate departureDate) {

        requestService.getRoute(trainNumber,departureDate);
    }
}
