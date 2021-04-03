package com.projects.universityapiconsumer.service;

import java.net.URI;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.projects.universityapiconsumer.model.UpdateResponseEntity;

import reactor.util.retry.Retry;

@Service
public class SchedulerService {

    @Autowired
    private WebClient webClientUpdate;

    @Value("${external.api.host:http://localhost:5000}")
    private String apiUrl;

    private static final Logger LOG = LoggerFactory.getLogger(SchedulerService.class);
    private static String SUCCESS = "success";
    private static String ERROR = "error";


    @Scheduled(cron = "0 59 23 * * *")
    private synchronized void updateExternalApi() {
        UpdateResponseEntity response = webClientUpdate.get()
                .uri(URI.create(getRestDestinationUrl()))
                .retrieve()
                .bodyToMono(UpdateResponseEntity.class)
                .retryWhen(Retry.fixedDelay(10, Duration.ofSeconds(5)))
                .delaySubscription(Duration.ofSeconds(10))
                .block();
        if (isSuccessful(response)) {
            LOG.info(response.getMessage());
        } else if(isError(response)) {
            LOG.error(response.getMessage());
        }

    }

    private String getRestDestinationUrl() {
        StringBuilder restDestination = new StringBuilder(apiUrl + "/update");
        return restDestination.toString();
    }

    private boolean isSuccessful(UpdateResponseEntity response) {
        if (response == null) {
            return false;
        }
        return response.getStatus().equals(SUCCESS);
    }

    private boolean isError(UpdateResponseEntity response) {
        if (response == null) {
            return false;
        }
        return response.getStatus().equals(ERROR);
    }


}
