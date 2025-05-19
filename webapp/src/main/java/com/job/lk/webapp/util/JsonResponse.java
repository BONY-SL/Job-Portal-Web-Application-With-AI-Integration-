package com.job.lk.webapp.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonResponse {
    private int statusCode;
    private String status;
    private String message;
    private Object data;

    public JsonResponse(Object data) {
        this.data = data;
    }
}
