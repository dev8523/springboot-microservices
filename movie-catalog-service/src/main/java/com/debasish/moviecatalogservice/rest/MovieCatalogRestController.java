package com.debasish.moviecatalogservice.rest;

import com.debasish.moviecatalogservice.models.CatalogItem;
import com.debasish.moviecatalogservice.models.Movie;
import com.debasish.moviecatalogservice.models.Rating;
import com.debasish.moviecatalogservice.models.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
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

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable(name = "userId") String userId) {

        // It will map with the spring.application.name that we have provided in app.properties file.
        UserRating userRating = getUserRating(userId);

        // for each movie ID, call movie info service and get details
        return userRating.getUserRating()
                .stream()
                .map(this::getCatalogItem)
                .collect(Collectors.toList());
    }

    /**
     * Description for @HystrixCommand : We are telling hystrix this is the method which shouldn't cause the whole thing to go down.
     * We want to break the circuit when something goes down with this endpoint.
     * <p>
     * In any case there is a circuit breakdown, it will execute getFallbackCatalogItem() method as default.
     * <p>
     *
     * @param rating rating fields to be set.
     * @return List<CatalogItem>
     */
    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
    private CatalogItem getCatalogItem(Rating rating) {
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class); // It will map with the spring.application.name that we have provided in app.properties file.
        // Put them all together
        assert movie != null;
        return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
    }

    /**
     * This is the fallback method for getCatalogItem().
     *
     * @param rating
     * @return CatalogItem
     */
    private CatalogItem getFallbackCatalogItem(Rating rating) {
        return new CatalogItem("Movie Name Not Found", "NA", rating.getRating());
    }

    /**
     * Description for @HystrixCommand : We are telling hystrix this is the method which shouldn't cause the whole thing to go down.
     * We want to break the circuit when something goes down with this endpoint.
     * <p>
     * In any case there is a circuit breakdown, it will execute getFallbackUserRating() method as default.
     * <p>
     *
     * @param userId userId to be set.
     * @return List<CatalogItem>
     */
    @HystrixCommand(fallbackMethod = "getFallbackUserRating")
    private UserRating getUserRating(@PathVariable("userId") String userId) {
        return restTemplate.getForObject("http://rating-data-service/ratingsdata/users/" + userId, UserRating.class);
    }

    /**
     * This is the fallback method for getUserRating().
     *
     * @param userId
     * @return UserRating
     */
    private UserRating getFallbackUserRating(@PathVariable("userId") String userId) {
        return new UserRating(userId, Collections.singletonList(new Rating("0", 0)));
    }

    /**
     * This is the fallback method for the getCatalog endpoint. If that fails this will get execute by default.
     *
     * @param userId userId to be set.
     * @return List<CatalogItem>
     */
    public List<CatalogItem> getFallbackCatalog(@PathVariable(name = "userId") String userId) {
        return Collections.singletonList(new CatalogItem("No Movie", "", 0));
    }
}