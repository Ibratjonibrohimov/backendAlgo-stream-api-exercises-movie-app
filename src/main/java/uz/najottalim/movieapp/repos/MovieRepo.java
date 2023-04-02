package uz.najottalim.movieapp.repos;

import uz.najottalim.movieapp.db.SimpleDB;
import uz.najottalim.movieapp.models.Movie;

import java.util.List;

public class MovieRepo {
    public List<Movie> findAll() {
        return SimpleDB.movies;
    }
}
