package org.ocean.resttemplate;

import lombok.extern.slf4j.Slf4j;
import org.ocean.resttemplate.config.RestTemplateErrorHandler;
import org.ocean.resttemplate.config.RestTemplateInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.annotation.Documented;

@SpringBootApplication
@Slf4j
@RestController
public class RestTemplateApplication {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RestTemplateErrorHandler restTemplateErrorHandler;

    public RestTemplateApplication(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(RestTemplateApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner(RestTemplate restTemplate) {
        return args -> {

        };
    }


    /**
     * <h2>USAGE</h2>
     * {
     *     "headers":{
     *         "key":"value",
     *         "name":"saikumar"
     *     },
     *     "method":"GET" <b>any method like GET,POST,PUT,PATCH,DELETE</b>
     *     "url":"http://localhost:9091",
     *     "requestBody": <b>any data type</b>
     * }
     * @param restTemplateInfo
     * @return
     */
    @GetMapping
    public ResponseEntity<String> get(@RequestBody RestTemplateInfo restTemplateInfo) {
        // get
        ResponseEntity<String> response = restTemplate.getForEntity(restTemplateInfo.getUrl(), String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Received 200 OK with response: " + response.getBody());
            return ResponseEntity.ok(response.getBody());
        } else {
            log.error("Received 4xx Client Error: " + response.getBody());
            return ResponseEntity.ok(response.getBody());
        }
    }

    /**
     * <h2>with headers</h2>
     * @param requestInfo
     * @return
     */
    @GetMapping("/headers")
    public ResponseEntity<String> withHeaders(@RequestBody RestTemplateInfo requestInfo) {
        HttpHeaders headers = new HttpHeaders();
        if (null != requestInfo.getHeaders()) {
            requestInfo.getHeaders().forEach(headers::set);
            log.info(headers.toString());
        }
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(requestInfo.getUrl(), requestInfo.getMethod(), entity, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Received 200 OK with response: " + response.getBody());
            return ResponseEntity.ok(response.getBody());
        } else {
            log.error("Received 4xx Client Error: " + response.getBody());
            return ResponseEntity.ok(response.getBody());
        }
    }

    /**
     * with the <h2>POST</h2>
     * @param requestInfo
     * @return
     */
    @PostMapping
    public ResponseEntity<String> post(@RequestBody RestTemplateInfo requestInfo) {
        ResponseEntity<String> response = restTemplate.postForEntity(requestInfo.getUrl(), requestInfo.getRequestBody(), String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Received 200 OK with response: " + response.getBody());
            return ResponseEntity.ok(response.getBody());
        }
        log.error("Received 4xx Client Error: " + response.getBody());
        return ResponseEntity.ok(response.getBody());

    }

    /**
     * assume service is down
     * @param restTemplateInfo
     * @return
     */
    @GetMapping("/error")
    public ResponseEntity<String> getd(@RequestBody RestTemplateInfo restTemplateInfo) {

        RestTemplate restTemplate = new RestTemplate();
        // restTemplate error handler
        restTemplate.setErrorHandler(restTemplateErrorHandler);
        // get
        ResponseEntity<String> response = restTemplate.getForEntity(restTemplateInfo.getUrl(), String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Received 200 OK with response: " + response.getBody());
            return ResponseEntity.ok(response.getBody());
        } else {
            log.error("Received 4xx Client Error: " + response.getBody());
            return ResponseEntity.ok(response.getBody());
        }
    }

}
