package com.postal.service.model;

import lombok.Data;

@Data
public class ErrorResponse {
    private String timeStamp;
    private String reason;
}
