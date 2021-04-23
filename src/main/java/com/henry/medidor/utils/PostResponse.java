package com.henry.medidor.utils;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder
@Data
public class PostResponse {

    private String url;
    private HttpStatus status;

}
