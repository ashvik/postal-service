package com.postal.service.utils;

import com.postal.service.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class ErrorUtils {
    public static ResponseEntity<Mono<ErrorResponse>> buildErrorResponse(String errorReason, HttpStatus status) {
        Long currentTimeInMillis = System.currentTimeMillis();
        Date date = new Date(currentTimeInMillis);
        DateFormat dateFormat = new SimpleDateFormat("dd:MM:yy:HH:mm:ss");
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setReason(errorReason);
        errorResponse.setTimeStamp(dateFormat.format(date));

        return ResponseEntity
                .status(status)
                .body(Mono.just(errorResponse));
    }

    public static <T> Mono<ResponseEntity> checkEmptyFluxForNotFound(Flux<T> in, String errorMsgIfEmpty) {
        return in.hasElements()
                .map(isAvailable -> {
                    if (isAvailable) {
                        return ResponseEntity.ok().body(in);
                    } else {
                        return ErrorUtils
                                .buildErrorResponse(errorMsgIfEmpty, HttpStatus.NOT_FOUND);
                    }
                });
    }
}
