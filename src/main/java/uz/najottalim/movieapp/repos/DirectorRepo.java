package uz.najottalim.movieapp.repos;

import uz.najottalim.movieapp.db.SimpleDB;
import uz.najottalim.movieapp.models.Director;

import java.util.List;

public class DirectorRepo {
    public List<Director> findAll() {
        return SimpleDB.directors;
    }
}
