package com.debasish.moviecatalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
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
     * Description for @Bean : Spring is gonna execute this method  whatever you return, it is gonna say hey RestTemplate maps to this instance.
     * Anybody autowires RestTemplate they get this one instance. This method executes just once.
     *
     * Description for @LoadBalanced : It does the service discovery in a load balanced way. Basically we are telling RestTemplate
     * the URL we will be providing is just a hint about what service you need to discover.
     *
     * @return RestTemplate - returns a RestTemplate instance
     */
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

	/*@Bean
	public WebClient.Builder getWebClientbuilder() {
		return new WebClient.Builder();
	}*/
}
