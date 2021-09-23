package com.debasish.moviecatalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class MovieCatalogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieCatalogServiceApplication.class, args);
    }

    /**
     * Spring is gonna execute this method  whatever you return, it is gonna say hey RestTemplate maps to this instance.
     * Anybody autowires RestTemplate they get this one instance. This method executes just once.
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
