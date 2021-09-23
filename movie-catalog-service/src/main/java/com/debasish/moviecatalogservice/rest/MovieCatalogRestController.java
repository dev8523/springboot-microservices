package com.debasish.moviecatalogservice.rest;

import com.debasish.moviecatalogservice.models.CatalogItem;
import com.debasish.moviecatalogservice.models.Movie;
import com.debasish.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogRestController {

    /**
     * As long as we autowired this, we are telling spring somebody has a bean somewhere of type RestTemplate, inject me that thing.
     */
    @Autowired
    private RestTemplate restTemplate;

    /*
    * Use this if you want to do advanced load balancing. It executes the bean at the start of the spring app.
    */
    @Autowired
    private DiscoveryClient discoveryClient;

    /*@Autowired
    private WebClient.Builder webClientBuilder;*/

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalogs(@PathVariable(name = "userId") String userId) {

        // It will map with the spring.application.name that we have provided in app.properties file.
        UserRating ratings = restTemplate.getForObject("http://rating-data-service/ratingsdata/users/" + userId, UserRating.class);

        return ratings.getUserRating().stream().map(rating -> {
            // for each movie ID, call movie info service and get details
            Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class); // It will map with the spring.application.name that we have provided in app.properties file.
            // Put them all together
            return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
        }).collect(Collectors.toList());

    }
}


/*
            Movie movie = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8083/ratingsdata/movies/" + rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();
*/