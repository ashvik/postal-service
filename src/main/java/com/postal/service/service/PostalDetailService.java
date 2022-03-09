package com.postal.service.service;

import com.postal.service.model.PostalDetail;
import com.postal.service.repositories.PostalDetailRepository;
import io.r2dbc.spi.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class PostalDetailService {
    @Autowired
    private PostalDetailRepository postalDetailRepository;

    @Autowired
    private DatabaseClient databaseClient;

    public PostalDetailService() {
    }

    public Flux<PostalDetail> findByCityName(String cityName) {
        return postalDetailRepository.findByCityNameIgnoreCase(cityName);
    }

    public Flux<PostalDetail> findByAreaName(String areaName) {
        return postalDetailRepository.findByAreaNameContainingIgnoreCase(areaName);
    }

    public Flux<PostalDetail> filterByAreaNameAndCityName(String area, String city) {
        return postalDetailRepository.filterByAreaNameAndCityName(area, city);
    }

    @Transactional
    public Flux<Long> insert(List<PostalDetail> data) {
        return this.databaseClient.inConnectionMany(connection -> {

            Statement statement = connection
                    .createStatement("insert into  public.postal_details (city_name, area_name, postal_code) " +
                    "values ($1, $2, $3)")
                    .returnGeneratedValues("id");

            for (PostalDetail p : data) {
                statement.bind(0, p.getCityName()).bind(1, p.getAreaName()).bind(2, p.getPostalCode()).add();
            }

            return Flux.from(statement.execute())
                    .flatMap(result -> result.map((row, rowMetadata) -> row.get("id", Long.class)));
        });
    }

    @Transactional
    public Flux<Long> update(List<PostalDetail> data) {
        return this.postalDetailRepository.saveAll(data).map(pd -> pd.getId());
    }

    @Transactional
    public Mono<Void> delete(List<Long> data) {
        return this.postalDetailRepository.deleteAllById(data);
    }

}
