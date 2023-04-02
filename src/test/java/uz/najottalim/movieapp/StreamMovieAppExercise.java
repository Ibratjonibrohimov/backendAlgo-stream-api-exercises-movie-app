package uz.najottalim.movieapp;


import org.junit.jupiter.api.*;

import lombok.extern.slf4j.Slf4j;
import uz.najottalim.movieapp.models.Director;
import uz.najottalim.movieapp.models.Genre;
import uz.najottalim.movieapp.models.Movie;
import uz.najottalim.movieapp.repos.DirectorRepo;
import uz.najottalim.movieapp.repos.GenreRepo;
import uz.najottalim.movieapp.repos.MovieRepo;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class StreamMovieAppExercise {

    private DirectorRepo directorRepo;
    private GenreRepo genreRepo;
    private MovieRepo movieRepo;

    @BeforeEach
    void setUp() {
        directorRepo = new DirectorRepo();
        genreRepo = new GenreRepo();
        movieRepo = new MovieRepo();
    }

    @Test
    @DisplayName("Hammasini ko'rish")
    public void printAll() {
        // System.out.println o'rniga shuni ishlatingla
        log.info("Movies:");
        movieRepo.findAll()
                .forEach(p -> log.info(p.toString() + '\n'));
        log.info("Directors:");
        directorRepo.findAll()
                .forEach(p -> log.info(p.toString() + '\n'));
        log.info("Genres:");
        genreRepo.findAll()
                .forEach(p -> log.info(p.toString() + '\n'));
    }

    @Test
    @DisplayName("Janri 'Drama' yoki 'Komediya' bo'lgan kinolarni toping")
    public void exercise1() {
        List<Movie> movies = movieRepo.findAll()
                .stream()
                .filter(movie ->
                        movie.getGenres()
                                .stream()
                                .anyMatch(genre ->
                                        genre.getName().equalsIgnoreCase("Drama")
                                                || genre.getName().equalsIgnoreCase("Komediya")))
                .collect(Collectors.toList());
        movies.forEach(movie -> {
            System.out.println(movie.getTitle() +
                    " -> janrlari: " +
                    movie.getGenres()
                            .stream()
                            .map(Genre::getName)
                            .collect(Collectors.toList()));
        });
    }

    @Test
    @DisplayName("Har bitta rejissorning olgan kinolar sonini chiqaring")
    public void exercise2() {
        Map<String, Integer> collect = directorRepo.findAll().stream()
                .collect(Collectors.toMap(
                        director -> director.getName(),
                        director -> director.getMovies().size()
                ));
        collect.forEach((s, integer) -> {
            System.out.println(s+
                    "  kinolar soni:"+ integer);
        });

    }

    @Test
    @DisplayName("Eng oldin olingan kinoni chiqaring")
    public void exercise8() {
        List<Movie> collect = movieRepo.findAll().stream()
                .sorted(Comparator.comparingInt(Movie::getYear))
                .limit(1)
                .collect(Collectors.toList());
        System.out.println(collect.get(0));

    }

    @Test
    @DisplayName("2004 chi yilda kinolaga sarflangan umumiy summani chiqaring")
    public void exercise9() {
        double sum = movieRepo.findAll().stream()
                .filter(movie -> movie.getYear() == 2004)
                .mapToDouble(value -> value.getSpending())
                .sum();
        System.out.println(sum);
    }

    @Test
    @DisplayName("har bir yilda olingan kinolarni o'rtacha reytingini chiqaring")
    public void exercise10() {
        Map<Integer, String> collect = movieRepo.findAll().stream()
                .collect(Collectors.groupingBy(
                        movie -> movie.getYear(),
                        Collectors.mapping(movie -> movie.getRating(), Collectors.toList())
                ))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        integerListEntry -> integerListEntry.getKey(),
                        integerListEntry -> String.format("% .02f",integerListEntry.getValue()
                                .stream()
                                .mapToDouble(value -> value)
                                .average()
                                .getAsDouble())
                ));
        System.out.println(collect);
    }

    @Test
    @DisplayName("Janri 'Drama' bo'lgan eng tarixda suratga olingan kinoni chiqaring")
    public void exercise11() {
        Optional<Movie> drama = movieRepo.findAll().stream()
                .filter(movie -> movie.getGenres().contains("Drama"))
                .sorted((o1, o2) -> Integer.compare(o1.getYear(), o2.getYear()))
                .findFirst();
        System.out.println(drama);
    }

    @Test
    @DisplayName("Qaysi janrdagi kinolar eng ko'p oscar olganligini chiqaring")
    public void exercise12() {
        Optional<Map.Entry<String, Long>> first = movieRepo.findAll()
                .stream()
                .filter(movie -> movie.isWinOscar())
                .flatMap(movie -> movie.getGenres().stream())
                .collect(Collectors.groupingBy(
                        genre -> genre.getName(),
                        Collectors.counting()
                )).entrySet()
                .stream()
                .sorted((o1, o2) -> Long.compare(o2.getValue(), o1.getValue()))
                .findFirst();
        System.out.println(first);
    }

    @Test
    @DisplayName("Har bir rejissorni olgan kinolarining janrini soni chiqaring" +
            "Misol uchun: " +
            "Aziz Aliev suratga olgan" +
            "Komediya    : 2\n" +
            "Drama       : 5\n" +
            "Romantika   : 2")
    public void exercise3() {

        Map<String, Map<String, Long>> collect = directorRepo.findAll()
                .stream()
                .collect(Collectors.toMap(
                        director -> director.getName(),
                        director -> director.getMovies().stream()
                                .flatMap(movie -> movie.getGenres().stream())
                                .collect(Collectors.toList()).stream()
                                .collect(Collectors.groupingBy(
                                        genre -> genre.getName(),
                                        Collectors.counting()
                                ))
                ));

        collect.forEach(
                (s, stringLongMap) ->{
                    System.out.println(s+"suratga olgan:");
                            stringLongMap.forEach(
                                    (s1, aLong) -> {
                                        System.out.println(s1+":"+aLong);
                                    }

                            );
                    System.out.println();
                }
        );


    }

    @Test
    @DisplayName("2004 chi yilda chiqqan kinolar orasida eng ko'p pul sarflanganini chiqaring")
    public void exercise4() {

    }

    @Test
    @DisplayName("har bitta rejissor olgan kinolarining o'rtacha ratingini chiqaring" +
            "Misol uchun:" +
            "Sardor Muhammadaliev: 2.23" +
            "Akrom Aliev: 2.33")
    public void exercise5() {

    }

    @Test
    @DisplayName("Rejissolarni umumiy kinolari uchun olgan oskarlari soni bo'yicha saralab chiqaring")
    public void exercise6() {

    }

    @Test
    @DisplayName("2004 yilda olingan Komediya kinolariga ketgan umumiy summani chiqaring")
    public void exercise7() {

    }


    @Test
    @DisplayName("Kinolarni chiqgan yili bo'yicha guruhlab, ratingi bo'yicha saralab toping")
    public void exercise13() {

    }

    @Test
    @DisplayName("Har bitta rejiossor qaysi janrda o'rtacha reytingi eng ko'p ekanligini chiqaring")
    public void exercise14() {

    }

    @Test
    @DisplayName("Eng kam kinolarga pul sarflagan rejissorni chiqaring")
    public void exercise15() {

    }

    @Test
    @DisplayName("Komediya kinolarini, 2000 chi yildan keyin olinganlarga ketgan narxni DoubleSummaryStatisticasini chiqaring")
    public void exercise16() {

    }

    @Test
    @DisplayName("Qaysi kinoni eng ko'p rejissorlar birgalikda olishgan va ratingi eng baland chiqqan")
    public void exercise17() {

    }

    @Test
    @DisplayName("Har bir janrdagi kino nomlari umumiy nechta so'zdan iborat")
    public void exercise18() {

    }

    @Test
    @DisplayName("Har bir asrda olingan kinolarni o'rtacha reytingini chiqaring")
    public void exercise19() {

    }

    @Test
    @DisplayName("Ismi A harfi bilan boshlanadigan rejissorlarni olgan kinolarini o'rtacha ratingi bo'yicha saralab chiqaring")
    public void exercise20() {

    }
}
