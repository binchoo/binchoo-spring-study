package org.binchoo.study.spring.resttemplate.movieservice.service;

import org.binchoo.study.spring.resttemplate.movieservice.entity.Movie;
import org.binchoo.study.spring.resttemplate.movieservice.entity.MovieResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class MovieClientService implements IMovieClientService {

    private static Logger logger = LoggerFactory.getLogger(MovieClientService.class);

    private static String URL_MOVIE_API = "http://boostcourse-appapi.connect.or.kr:10000";

    private static String URL_READ_MOVIE_LIST = URL_MOVIE_API + "/movie/readMovieList";

    private static String URL_READ_MOVIE = URL_MOVIE_API + "/movie/readMovie";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Movie> readMovieList() {
        MovieResponse response = restTemplate.getForObject(URL_READ_MOVIE_LIST, MovieResponse.class);
        return Optional.of(response.getResult())
                .orElseThrow(InternalError::new);
    }

    @Override
    public List<Movie> readMovie(Long id, String name) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(URL_READ_MOVIE);
        Optional.ofNullable(id).ifPresent(it-> uriBuilder.queryParam("id", it));
        Optional.ofNullable(name).ifPresent(it-> uriBuilder.queryParam("name", name));

        String url = uriBuilder.build().toUriString(); // build string without encoding
        MovieResponse response = restTemplate.getForObject(url, MovieResponse.class); // because restTemplate will encodes the url
        return Optional.of(response.getResult())
                .orElseThrow(InternalError::new);
    }
}
