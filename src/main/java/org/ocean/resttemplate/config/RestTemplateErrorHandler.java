package org.ocean.resttemplate.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;

@Component
@Slf4j
public class RestTemplateErrorHandler implements ResponseErrorHandler {

    private static void errorLog(ClientHttpResponse response) throws IOException {
        log.info("Error occurred while processing request");
        log.info("Status code: {}", response.getStatusCode());
        log.info("Body: {}", response.getBody());
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        errorLog(response);
        return response.getStatusCode().is2xxSuccessful();
    }

    // custom handler
    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        errorLog(response);
    }

}
