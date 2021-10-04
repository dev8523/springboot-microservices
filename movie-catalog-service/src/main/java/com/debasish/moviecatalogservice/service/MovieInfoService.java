package com.debasish.moviecatalogservice.service;

import com.debasish.moviecatalogservice.models.CatalogItem;
import com.debasish.moviecatalogservice.models.Movie;
import com.debasish.moviecatalogservice.models.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfoService {

    /**
     * As long as we autowired this, we are telling spring somebody has a bean somewhere of type RestTemplate, inject me that thing.
     */
    @Autowired
    private RestTemplate restTemplate;

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
    @HystrixCommand(
            fallbackMethod = "getFallbackCatalogItem",
            threadPoolKey = "movieInfoPool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "20"),
                    @HystrixProperty(name = "maxQueueSize", value = "10"),
            }
    )
    public CatalogItem getCatalogItem(Rating rating) {
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class); // It will map with the spring.application.name that we have provided in app.properties file.
        // Put them all together
        assert movie != null;
        return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
    }

    /**
     * This is the fallback method for getCatalogItem(). If that fails this will get execute by default.
     *
     * @param rating
     * @return CatalogItem
     */
    public CatalogItem getFallbackCatalogItem(Rating rating) {
        return new CatalogItem("Movie Name Not Found", "Fallback method called as service unavailable", rating.getRating());
    }
}
