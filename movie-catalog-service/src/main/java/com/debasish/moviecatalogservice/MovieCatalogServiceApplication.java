package com.debasish.moviecatalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilderFactory;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@SpringBootApplication
public class MovieCatalogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieCatalogServiceApplication.class, args);
    }

	/**
	 * Spring is gonna execute this method  whatever you return, it is gonna say hey RestTemplate maps to this instance.
	 * 	Anybody autowires RestTemplate they get this one instance. This method executes just once.
	 *
	 * @return RestTemplate - returns a RestTemplate instance
	 */
	@Bean
    public RestTemplate getRestTemplate() {
		return new RestTemplate();
    }

	/*@Bean
	public WebClient.Builder getWebClientbuilder() {
		return new WebClient.Builder();
	}*/
}
