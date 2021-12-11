package org.binchoo.study.spring.resttemplate.movieservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.joda.time.DateTime;

@ToString
@EqualsAndHashCode
@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Movie {

    Long id;

    String title;

    String titleEng;

    @JsonFormat(pattern = "yyyy-MM-dd")
    DateTime date;

    Float userRating;

    Float audienceRating;

    Float reviewerRating;

    Float reservationRate;

    Long reservationGrade;

    Long grade;

    String thumb;

    String image;

    String photos;

    String videos;

    String outlinks;

    String genre;

    Long duration;

    Long audience;

    String synopsis;

    String director;

    String actor;

    Long like;

    Long dislike;
}
