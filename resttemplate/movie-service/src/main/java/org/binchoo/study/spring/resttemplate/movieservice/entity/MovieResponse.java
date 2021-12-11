package org.binchoo.study.spring.resttemplate.movieservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class MovieResponse {

    String message;

    Integer code;

    String resultType;

    List<Movie> result;
}
