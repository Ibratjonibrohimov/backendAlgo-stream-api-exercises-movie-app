package uz.najottalim.movieapp.db;

import uz.najottalim.movieapp.models.Director;
import uz.najottalim.movieapp.models.Genre;
import uz.najottalim.movieapp.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class SimpleDB {
    public static final List<Director> directors = List.of(
            new Director(1, "Muhammad Mustafoyev", new ArrayList<>()),
            new Director(2, "Abdulloh Imronov", new ArrayList<>()),
            new Director(3, "Aziz Aliev", new ArrayList<>()),
            new Director(4, "Umar Muhammadyusupov", new ArrayList<>()),
            new Director(5, "Abduaziz Temirov", new ArrayList<>()),
            new Director(6, "Amir Ismoilov", new ArrayList<>()),
            new Director(7, "Firdavs Otabekov", new ArrayList<>()),
            new Director(8, "Shahzoda Matchonova", new ArrayList<>()),
            new Director(9, "Asad Kamilov", new ArrayList<>()),
            new Director(10, "Kamron Odilov", new ArrayList<>()),
            new Director(11, "Amir Shohruxov", new ArrayList<>()),
            new Director(12, "Muhammadamin Abubakrov", new ArrayList<>()),
            new Director(13, "Zubayr Islomov", new ArrayList<>()),
            new Director(14, "Amir Behruzov", new ArrayList<>()),
            new Director(15, "Sardor Muhammadaliev", new ArrayList<>())
    );
    public static final List<Movie> movies = List.of(
            new Movie(1, "Shakra Premyera", 2004, 122342345.323, 4.11, true, new ArrayList<>(), new ArrayList<>()),
            new Movie(2, "Marlou Premyera", 2004, 12323535.3234, 3.23, true, new ArrayList<>(), new ArrayList<>()),
            new Movie(3, "12 Qavat", 1999, 423423.11234, 2.123, true, new ArrayList<>(), new ArrayList<>()),
            new Movie(4, "Ko'cha jangchisi", 1999, 122342343.3234, 3.333, false, new ArrayList<>(), new ArrayList<>()),
            new Movie(5, "Falokat", 1987, 1234123.354, 4.32, true, new ArrayList<>(), new ArrayList<>()),
            new Movie(6, "O'tmish soyasi", 2012, 145323.3345, 3.123, false, new ArrayList<>(), new ArrayList<>()),
            new Movie(7, "Iblis qutisi 2", 2008, 523423.3145, 2.124, true, new ArrayList<>(), new ArrayList<>()),
            new Movie(8, "Lyuter Qulagan Quyosh", 1999, 543123.351, 4.898, false, new ArrayList<>(), new ArrayList<>()),
            new Movie(9, "Siyosatchilar", 2008, 12341252343.352, 4.92, true, new ArrayList<>(), new ArrayList<>()),
            new Movie(10, "Guvoh", 2023, 622423.323, 1.463, false, new ArrayList<>(), new ArrayList<>()),
            new Movie(11, "Jon Uik 4", 2004, 65423.3256, 2.1254, false, new ArrayList<>(), new ArrayList<>()),
            new Movie(12, "Sipr", 2004, 76423.3256, 3.15, false, new ArrayList<>(), new ArrayList<>()),
            new Movie(13, "Odam ovi", 1898, 76123.35678, 4.423, true, new ArrayList<>(), new ArrayList<>()),
            new Movie(14, "Pistirma tuzoq", 1945, 956123.35679, 3.123, false, new ArrayList<>(), new ArrayList<>()),
            new Movie(15, "O'g'irlangan shaxsiyat", 1945, 965123.3, 2.451, false, new ArrayList<>(), new ArrayList<>())
    );
    public static final List<Genre> genres = List.of(
            new Genre(1, "Jangari"),
            new Genre(2, "Drama"),
            new Genre(3, "Komediya"),
            new Genre(4, "Melodrama"),
            new Genre(5, "Sarguzasht"),
            new Genre(6, "Qo'rqinchli"),
            new Genre(7, "Tarixiy"),
            new Genre(8, "Klassika"),
            new Genre(9, "Fantastika"),
            new Genre(10, "Romantika")
    );

    static {
        int[][] directorToMovies = new int[][]{
                new int[]{1, 8, 9, 10},
                new int[]{2, 7, 8, 11},
                new int[]{2, 3, 4, 11},
                new int[]{1, 5, 6, 12, 14},
                new int[]{3, 5, 7, 10},
                new int[]{1, 2, 12},
                new int[]{3, 5, 4, 13, 14, 15},
                new int[]{1, 8, 6, 11},
                new int[]{2, 5, 6, 15},
                new int[]{3, 5, 4, 10},
                new int[]{1, 8, 7, 13},
                new int[]{9, 2, 5, 11},
                new int[]{1, 5, 6, 12, 13},
                new int[]{3, 2, 7, 10},
                new int[]{9, 8, 6, 13, 15},
        };
        int[][] movieToGenres = new int[][]{
                new int[]{1, 8, 9, 10},
                new int[]{2, 7, 8,},
                new int[]{2, 3, 4,},
                new int[]{1, 5, 6,},
                new int[]{3, 5, 7, 10},
                new int[]{1, 2},
                new int[]{3, 5, 4},
                new int[]{1, 8, 6},
                new int[]{2, 5, 6},
                new int[]{3, 5, 4, 10},
                new int[]{1, 8, 7},
                new int[]{9, 2, 5},
                new int[]{1, 5, 6},
                new int[]{3, 2, 7, 10},
                new int[]{9, 8, 6},
        };
        int[][] movieToDirectories = new int[][]{
                new int[]{1, 8, 9, 10},
                new int[]{2, 7, 8, 11},
                new int[]{2, 3, 4, 11},
                new int[]{1, 5, 6, 12, 14},
                new int[]{3, 5, 7, 10},
                new int[]{1, 2, 12},
                new int[]{3, 5, 4, 13, 14, 15},
                new int[]{1, 8, 6, 11},
                new int[]{2, 5, 6, 15},
                new int[]{3, 5, 4, 10},
                new int[]{1, 8, 7, 13},
                new int[]{9, 2, 5, 11},
                new int[]{1, 5, 6, 12, 13},
                new int[]{3, 2, 7, 10},
                new int[]{9, 8, 6, 13, 15},
        };
        for (int i = 0; i < directors.size(); i++) {
            var movieList = directors.get(i).getMovies();
            for (int j = 0; j < directorToMovies[i].length; j++) {
                movieList.add(movies.get(directorToMovies[i][j] - 1));
            }
        }
        for (int i = 0; i < movies.size(); i++) {
            var directorList = movies.get(i).getDirectors();
            for (int j = 0; j < movieToDirectories[i].length; j++) {
                directorList.add(directors.get(movieToDirectories[i][j] - 1));
            }
            var genreList = movies.get(i).getGenres();
            for (int j = 0; j < movieToGenres[i].length; j++) {
                genreList.add(genres.get(movieToGenres[i][j] - 1));
            }
        }
    }

}
