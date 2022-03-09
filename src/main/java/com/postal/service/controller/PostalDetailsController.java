package com.postal.service.controller;

import com.postal.service.model.PostalDetail;
import com.postal.service.service.PostalDetailService;
import com.postal.service.utils.ErrorUtils;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/postal")
public class PostalDetailsController {

    @Autowired
    private PostalDetailService postalDetailService;

    @GetMapping("/city/{city}")
    public Mono<ResponseEntity> getPostalsByCity(@PathVariable("city") String city) {
        return ErrorUtils
                .checkEmptyFluxForNotFound(postalDetailService.findByCityName(city)
                        , "No postal record for city: " + city);
    }

    @GetMapping("/area/{area}")
    public Mono<ResponseEntity> getPostalsByArea(@PathVariable("area") String area) {
        return ErrorUtils
                .checkEmptyFluxForNotFound(postalDetailService.findByAreaName(area)
                        , "No postal record for area: " + area);
    }

    @GetMapping("/filter")
    public Mono<ResponseEntity> filterByAreaAndCity(@RequestParam("area") String area, @RequestParam("city") String city) {
        return ErrorUtils
                .checkEmptyFluxForNotFound(postalDetailService.filterByAreaNameAndCityName(area, city)
                        , "No postal record for city: "+ city+" and area: "+area);
    }

    @PostMapping("/insert")
    public <T> ResponseEntity<Publisher<T>> insertPostalDetails(@RequestBody List<PostalDetail> postalDetails) {
        List<PostalDetail> postalDetailList = postalDetails
                .stream()
                .filter(pd -> pd.hasIdentity())
                .collect(Collectors.toList());

        if (!postalDetailList.isEmpty()) {
            return (ResponseEntity) ErrorUtils
                    .buildErrorResponse("Id should not present in payload for insert!!", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body((Flux<T>) postalDetailService.insert(postalDetails));
    }

    @PutMapping("/update")
    public <T> ResponseEntity<Publisher<T>> updatePostalDetails(@RequestBody List<PostalDetail> postalDetails) {
        List<PostalDetail> postalDetailList = postalDetails
                .stream()
                .filter(pd -> !pd.hasIdentity())
                .collect(Collectors.toList());

        if (!postalDetailList.isEmpty()) {
            return (ResponseEntity) ErrorUtils
                    .buildErrorResponse("Id is required in payload for update!!", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body((Flux<T>) postalDetailService.update(postalDetails));
    }

    @DeleteMapping("/delete")
    public Mono<Void> deletePostalDetailsById(@RequestBody List<Long> ids) {
        return postalDetailService.delete(ids);
    }
}
