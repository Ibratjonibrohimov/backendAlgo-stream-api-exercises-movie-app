package uz.najottalim.movieapp.repos;

import uz.najottalim.movieapp.db.SimpleDB;
import uz.najottalim.movieapp.models.Genre;

import java.util.List;

public class GenreRepo {
    public List<Genre> findAll() {
        return SimpleDB.genres;
    }
}
