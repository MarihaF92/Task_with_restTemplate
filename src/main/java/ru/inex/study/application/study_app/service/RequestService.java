package ru.inex.study.application.study_app.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.inex.study.application.study_app.domain.RouteResponse;
import ru.inex.study.application.study_app.repository.RequestRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;

    private final RestTemplate restTemplate;

    public void getRoute(String trainNumber, LocalDate departureDate){
        String url = "http://localhost:8080/route?trainNumber={trainNumber}&departureDate={departureDate}";

        RouteResponse response = restTemplate.getForObject(url, RouteResponse.class, trainNumber, departureDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        requestRepository.insertBatch(response.getRoutes(),trainNumber,departureDate);

        }
}



