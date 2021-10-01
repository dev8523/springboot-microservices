package com.debasish.moviecatalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableHystrixDashboard
public class MovieCatalogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieCatalogServiceApplication.class, args);
    }

    /**
     * Description for @Bean : Spring is gonna execute this method  whatever you return, it is gonna say hey RestTemplate maps to this instance.
     * Anybody autowires RestTemplate they get this one instance. This method executes only once.
     * <p>
     * Description for @LoadBalanced : It does the service discovery in a load balanced way. Basically we are telling RestTemplate
     * the URL we will be providing is just a hint about what service you need to discover.
     * <p>
     * Setting Timeout : Wih this RestTemplate, As long as the response comes back within 3 secs it's all good. If response doesn't come back
     * it is going to throw an error.
     * <p>
     *
     * @return RestTemplate - returns a RestTemplate instance
     */
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(3000); // Timeout has been set to 3 sec.
        return new RestTemplate(clientHttpRequestFactory);
    }

	/*@Bean
	public WebClient.Builder getWebClientbuilder() {
		return new WebClient.Builder();
	}*/
}
