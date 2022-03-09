package com.postal.service.repositories;

import com.postal.service.model.PostalDetail;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PostalDetailRepository extends ReactiveCrudRepository<PostalDetail,Long> {
    Flux<PostalDetail> findByCityNameIgnoreCase(String cityName);

    Flux<PostalDetail> findByAreaNameContainingIgnoreCase(String areaName);

    @Query("select * from public.postal_details pd where pd.area_name like concat('%', :area, '%') and city_name = :city")
    Flux<PostalDetail> filterByAreaNameAndCityName(@Param("area") String area, @Param("city") String city);
}
