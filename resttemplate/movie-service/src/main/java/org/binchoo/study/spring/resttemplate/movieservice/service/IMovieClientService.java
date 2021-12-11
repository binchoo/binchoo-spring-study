package org.binchoo.study.spring.resttemplate.movieservice.service;

import org.binchoo.study.spring.resttemplate.movieservice.entity.Movie;

import java.util.List;

public interface IMovieClientService {
    List<Movie> readMovieList();
    List<Movie> readMovie(Long id, String name);
}
