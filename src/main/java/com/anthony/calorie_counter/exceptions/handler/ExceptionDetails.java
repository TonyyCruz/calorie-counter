package com.anthony.calorie_counter.exceptions.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;

@Data @AllArgsConstructor @NoArgsConstructor
public class ExceptionDetails implements Serializable {
    String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    Instant timestamp;
    int status;
    String exception;
    String path;
    Map<String, String> details;
}