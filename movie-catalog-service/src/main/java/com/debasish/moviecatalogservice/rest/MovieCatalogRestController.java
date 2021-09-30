package com.debasish.moviecatalogservice.rest;

import com.debasish.moviecatalogservice.models.CatalogItem;
import com.debasish.moviecatalogservice.models.UserRating;
import com.debasish.moviecatalogservice.service.MovieInfoService;
import com.debasish.moviecatalogservice.service.UserRatingInfoService;
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

    @Autowired
    private MovieInfoService movieInfoService;

    @Autowired
    private UserRatingInfoService userRatingInfoService;

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable(name = "userId") String userId) {

        // It will map with the spring.application.name that we have provided in app.properties file.
        UserRating userRating = userRatingInfoService.getUserRating(userId);

        // for each movie ID, call movie info service and get details
        return userRating.getUserRating()
                .stream()
                .map(rating -> movieInfoService.getCatalogItem(rating))
                .collect(Collectors.toList());
    }

}