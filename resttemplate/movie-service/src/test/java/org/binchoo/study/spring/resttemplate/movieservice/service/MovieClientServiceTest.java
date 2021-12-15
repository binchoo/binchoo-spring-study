package org.binchoo.study.spring.resttemplate.movieservice.service;

import org.binchoo.study.spring.resttemplate.movieservice.config.ClientConfig;
import org.binchoo.study.spring.resttemplate.movieservice.entity.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("proxy")
@SpringJUnitConfig(ClientConfig.class)
public class MovieClientServiceTest {

    @Autowired
    MovieClientService movieClientService;

    @Test
    void movieClientService_NotNull() {
        assertNotNull(movieClientService);
    }

    @Test
    void readMovieList() {
        List<Movie> movies = movieClientService.readMovieList();
        assert(movies.size() > 0);
    }

    @Test
    void readMovie() {
        List<Movie> movies1 = movieClientService.readMovie(1L, "몰루");
        List<Movie> movies2 = movieClientService.readMovie(null, "꾼");
        assertEquals(movies1.size(), movies2.size());

        Movie movie1 = movies1.get(0);
        Movie movie2 = movies2.get(0);
        assertEquals(movie1, movie2);
    }
}