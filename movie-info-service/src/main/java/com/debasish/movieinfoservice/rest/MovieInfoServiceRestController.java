package com.debasish.movieinfoservice.rest;


import com.debasish.movieinfoservice.models.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieInfoServiceRestController {

    @GetMapping("/{movieId}")
    public Movie getMovieInfo(@PathVariable(name = "movieId") String movieId) {
        return new Movie(movieId, "Test Name", "Test Description");
    }
}
