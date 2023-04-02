package uz.najottalim.movieapp.models;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    private int id;
    private String title;
    private int year;
    // qancha pul sarflangani
    private double spending;
    private double rating;
    // oscar yutgan yutmaganligini bildiradi
    private boolean winOscar;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Genre> genres;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Director> directors;

    {
        genres = new ArrayList<>();
        directors = new ArrayList<>();
    }

}
