package org.ocean.resttemplate.config;

import lombok.Data;
import org.springframework.http.HttpMethod;

import java.util.Map;

@Data
public class RestTemplateInfo {
    private String url;
    // GET , PUT , POST, PUT , DELETE , PATCH , OPTIONS
    private HttpMethod method;
    private Map<String,String> headers;
    private Map<String,String> parameters;
    private Map<String,String> cookies;
    private Object requestBody;
}
