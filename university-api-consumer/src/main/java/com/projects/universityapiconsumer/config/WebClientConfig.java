package com.projects.universityapiconsumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class WebClientConfig {

    private static final int BUFFER16MB = 16777216; //16 * 1024 * 1024;

    @Bean
    public WebClient webClient() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
          .codecs(configurer -> configurer
          .defaultCodecs()
          .maxInMemorySize(BUFFER16MB))
          .build();

        return WebClient.builder()
                .exchangeStrategies(exchangeStrategies)
                .build();
    }

    @Bean
    public WebClient webClientUpdate() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
          .codecs(this::acceptedCodecs)
          .build();

        return WebClient.builder()
                .exchangeStrategies(exchangeStrategies)
                .build();
    }

    private void acceptedCodecs(ClientCodecConfigurer clientCodecConfigurer) {
    clientCodecConfigurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(new ObjectMapper(), MediaType.TEXT_HTML));
    clientCodecConfigurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(new ObjectMapper(), MediaType.TEXT_HTML));
    clientCodecConfigurer.defaultCodecs().maxInMemorySize(BUFFER16MB);
    }
}
