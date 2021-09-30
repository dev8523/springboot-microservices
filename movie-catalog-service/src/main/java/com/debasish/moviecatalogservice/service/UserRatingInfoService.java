package com.debasish.moviecatalogservice.service;

import com.debasish.moviecatalogservice.models.Rating;
import com.debasish.moviecatalogservice.models.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class UserRatingInfoService {

    /**
     * As long as we autowired this, we are telling spring somebody has a bean somewhere of type RestTemplate, inject me that thing.
     */
    @Autowired
    private RestTemplate restTemplate;

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
    public UserRating getUserRating(@PathVariable("userId") String userId) {
        return restTemplate.getForObject("http://rating-data-service/ratingsdata/users/" + userId, UserRating.class);
    }

    /**
     * This is the fallback method for getUserRating(). If that fails this will get execute by default.
     *
     * @param userId userId to be set.
     * @return UserRating
     */
    public UserRating getFallbackUserRating(@PathVariable("userId") String userId) {
        return new UserRating(userId, Collections.singletonList(new Rating("0", 0)));
    }
}
