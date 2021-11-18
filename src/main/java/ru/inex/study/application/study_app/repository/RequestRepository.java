package ru.inex.study.application.study_app.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.inex.study.application.study_app.domain.StationInRoute;
import ru.inex.study.application.study_app.domain.Stops;
import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RequestRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public RequestRepository(@Qualifier("contentDbDataSource") DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public void insertBatch(final List<StationInRoute> routes, String trainNumber, LocalDate departureData){
        String sql = "INSERT INTO cwp.test_route " +
                "(train_number, departure_train_date,station_name, arrival_time, arrival_timeMSK, departure_time,departure_timeMSK) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        List<Object[]> parameters = new ArrayList<Object[]>();
        for (StationInRoute station : routes) {
            Stops stop = station.getStantion();
            parameters.add(new Object[] {trainNumber, departureData, stop.getName(), station.getArvTime(),station.getArvTimeMSK(),
                    station.getDepTime(), station.getDepTimeMSK()}
            );
        }
        jdbcTemplate.getJdbcTemplate().batchUpdate(sql, parameters);
    }

}
