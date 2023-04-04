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
import java.util.function.Function;
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
        Map<Director, Integer> collect = directorRepo.findAll().stream()
                .collect(Collectors.toMap(
                        director -> director,
                        director -> director.getMovies().size()
                ));
        collect.forEach((s, integer) -> {
            System.out.println(s.getName()+
                    "  kinolar soni:"+ integer);
        });

        System.out.println("2-usul");
        //---2-usul----

        Map<String, Long> collect1 = movieRepo.findAll().stream()
                .flatMap(movie -> movie.getDirectors().stream())
                .collect(Collectors.groupingBy(
                        director -> director.getName(),
                        Collectors.counting()
                ));
        collect1.forEach((s, integer) -> {
            System.out.println(s+
                    "  kinolar soni:"+ integer);
        });

    }

    @Test
    @DisplayName("Eng oldin olingan kinoni chiqaring")
    public void exercise8() {
        Movie movie = movieRepo.findAll().stream()
                .min((o1, o2) -> Integer.compare(o1.getYear(), o2.getYear()))
                .orElse(new Movie());


        //---------------2-usul---------------------------------
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
                .mapToDouble(Movie::getSpending)
                .sum();
        System.out.println(sum);
    }

    @Test
    @DisplayName("har bir yilda olingan kinolarni o'rtacha reytingini chiqaring")
    public void exercise10() {
        Map<Integer, String> collect = movieRepo.findAll().stream()
                .collect(Collectors.groupingBy(
                        Movie::getYear,
                        Collectors.mapping(Movie::getRating, Collectors.toList())
                ))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
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
                .filter(movie -> movie.getGenres().stream()
                        .map(genre -> genre.getName())
                        .collect(Collectors.toList()).contains("Drama"))
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
        Movie movie1 = movieRepo.findAll().stream()
                .filter(movie -> movie.getYear() == 2004)
                .sorted((o1, o2) -> Double.compare(o2.getSpending(), o1.getSpending()))
                .findFirst()
                .orElse(new Movie());
        System.out.println(movie1);

    }

    @Test
    @DisplayName("har bitta rejissor olgan kinolarining o'rtacha ratingini chiqaring" +
            "Misol uchun:" +
            "Sardor Muhammadaliev: 2.23" +
            "Akrom Aliev: 2.33")
    public void exercise5() {
        Map<String, String> collect = directorRepo.findAll().stream()
                .collect(Collectors.toMap(
                        director -> director.getName(),
                        director -> String.format("% .02f", director.getMovies().stream()
                                .mapToDouble(value -> value.getRating())
                                .average().getAsDouble())
                ));
        collect.forEach(
                (s, s2) -> {
                    System.out.println(
                            s+":"+s2
                    );
                }
        );

    }

    @Test
    @DisplayName("Rejissolarni umumiy kinolari uchun olgan oskarlari soni bo'yicha saralab chiqaring")
    public void exercise6() {
        Map<String, Long> collect = directorRepo.findAll().stream()
                .collect(Collectors.toMap(
                        Director::getName,
                        director -> (Long) director.getMovies().stream()
                                .filter(Movie::isWinOscar)
                                .count()
                ))
                .entrySet().stream()
                .sorted((o1, o2) -> Long.compare(o2.getValue(), o1.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
        collect.forEach(
                (s, aLong) -> {
                    System.out.println(s+":"+aLong+" ta  oskar ");
                }
        );

    }

    @Test
    @DisplayName("2004 yilda olingan Komediya kinolariga ketgan umumiy summani chiqaring")
    public void exercise7() {
        double komediya = movieRepo.findAll().stream()
                .filter(movie -> movie.getYear() == 2004)
                .peek(movie -> {
                    System.out.println(movie.getGenres());
                })
                .filter(movie -> movie.getGenres().contains("Komediya"))
                .peek(movie -> {
                    System.out.println(movie);
                })
                .mapToDouble(value -> value.getSpending())
                .sum();
        System.out.println(komediya);
    }


    @Test
    @DisplayName("Kinolarni chiqgan yili bo'yicha guruhlab, ratingi bo'yicha saralab toping")
    public void exercise13() {
        Map<Integer, List<Movie>> collect = movieRepo.findAll().stream()
                .collect(Collectors.groupingBy(
                        Movie::getYear
                ))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        integerListEntry -> integerListEntry.getValue().stream()
                                .sorted((o1, o2) -> Double.compare(o2.getRating(), o1.getRating()))
                                .collect(Collectors.toList())
                ));
        collect.forEach(
                (integer, movies) ->{
                    System.out.println(integer+" : ");
                    movies.forEach(
                         movie -> {
                             System.out.println("    "+movie.getTitle()+":"+movie.getRating());
                         }
                    );
                }

        );


    }

    @Test
    @DisplayName("Har bitta rejiossor qaysi janrda o'rtacha reytingi eng ko'p ekanligini chiqaring")
    public void exercise14() {
        Map<Director, Genre> collect = directorRepo.findAll().stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        director -> {
                            var movies = director.getMovies();
                            var genre = genreRepo.findAll().stream()
                                    .collect(Collectors.toMap(
                                            Function.identity(),
                                            genre1 ->
                                                    movies.stream().filter(
                                                                    movie -> movie.getGenres().contains(genre1))
                                                            .mapToDouble(movie -> movie.getRating())
                                                            .average()
                                                            .orElse(-1)

                                    ));
                            return genre.entrySet()
                                    .stream()
                                    .max((o1, o2) -> Double.compare(o1.getValue(), o2.getValue()))
                                    .get().getKey();
                        }
                ));
        collect.forEach((director, genre) -> System.out.println(director+":"+genre));

    }

    @Test
    @DisplayName("Eng kam kinolarga pul sarflagan rejissorni chiqaring")
    public void exercise15() {
        Map<String, Double> collect = directorRepo.findAll().stream()
                .collect(Collectors.toMap(
                        Director::getName,
                        director -> director.getMovies().stream()
                                .mapToDouble(Movie::getSpending)
                                .sum()
                ))
                .entrySet().stream()
                .sorted((o1, o2) -> Double.compare(o2.getValue(),o1.getValue()))
                .collect(Collectors.toMap(
                        stringDoubleEntry -> stringDoubleEntry.getKey(),
                        stringDoubleEntry -> stringDoubleEntry.getValue()
                ));
                collect.forEach((s, aDouble) -> {
                    System.out.println(s + ":" + aDouble);
                        }
                );
        System.out.println("-------------------------------------");
        Optional<Director> min = directorRepo.findAll().stream()
                .min((o1, o2) -> {
                    return Double.compare(
                            o1.getMovies().stream()
                                    .mapToDouble(value -> value.getSpending())
                                    .sum(),
                            o2.getMovies().stream()
                                    .mapToDouble(value -> value.getSpending())
                                    .sum());
                });
        System.out.println(min);

    }

    @Test
    @DisplayName("Komediya kinolarini, 2000 chi yildan keyin olinganlarga ketgan narxni DoubleSummaryStatisticasini chiqaring")
    public void exercise16() {
        DoubleSummaryStatistics komediya = movieRepo.findAll().stream()

                .filter(movie -> movie.getGenres().stream()
                        .map(genre -> genre.getName())
                        .collect(Collectors.toList()).contains("Komediya"))
                .filter(movie -> movie.getYear() > 2000)
                .peek(movie ->{
                    System.out.println(movie+":"+movie.getGenres());
                })
                .mapToDouble(value -> value.getSpending())
                .peek(value -> {
                    System.out.println("-----------------------------------------------");
                })
                .summaryStatistics();
        System.out.println(komediya);

    }

    @Test
    @DisplayName("Qaysi kinoni eng ko'p rejissorlar birgalikda olishgan va ratingi eng baland chiqqan")
    public void exercise17() {
        Optional<Movie> first = movieRepo.findAll().stream()
                .sorted(
                        (o1, o2) -> Integer.compare(
                                o2.getDirectors().size(),
                                o1.getDirectors().size()
                        )
                )
                .findFirst();
        Optional<Movie> first1 = movieRepo.findAll().stream()
                .filter(movie -> movie.getDirectors().size() == first.orElse(new Movie()).getDirectors().size())
                .peek(movie -> {
                    System.out.println(
                            movie.getDirectors().size()+" : "
                                    +movie.getRating()
                    );
                })
                .sorted((o1, o2) -> Double.compare(
                        o2.getRating(),
                        o1.getRating()
                ))
                .findFirst();
        System.out.println(first1.orElse(new Movie()));

    }

    @Test
    @DisplayName("Har bir janrdagi kino nomlari umumiy nechta so'zdan iborat")
    public void exercise18() {

        Map<Genre, Integer> collect = genreRepo.findAll().stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        genre1 -> {

                            var movies = movieRepo.findAll().stream();
                            return movies.filter(
                                            movie -> movie.getGenres()
                                                    .contains(genre1))
                                    .map(movie -> movie.getTitle())
                                    .reduce((s, s2) -> s +" "+ s2)
                                    .orElse("")
                                    .split(" ")
                                    .length;
                        }));
        collect.forEach((genre, integer) -> System.out.println(genre.getName()+":"+integer));


    }

    @Test
    @DisplayName("Har bir asrda olingan kinolarni o'rtacha reytingini chiqaring")
    public void exercise19() {
        Map<Integer, Double> collect = movieRepo.findAll().stream()
                .collect(Collectors.groupingBy(
                        movie -> movie.getYear() / 100
                ))
                .entrySet()
                .stream().collect(Collectors.toMap(
                        integerListEntry -> integerListEntry.getKey(),
                        integerListEntry -> integerListEntry.getValue().stream()
                                .mapToDouble(Movie::getRating).average().getAsDouble()
                ));
        collect.forEach(
                (integer, aDouble) -> {
                    System.out.println(integer +" : "+ aDouble);
                }
        );

    }

    @Test
    @DisplayName("Ismi A harfi bilan boshlanadigan rejissorlarni olgan kinolarini o'rtacha ratingi bo'yicha saralab chiqaring")
    public void exercise20() {
        Map<String, Double> collect = directorRepo.findAll().stream()
                .filter(director -> director.getName().charAt(0) == 'A')
                .collect(Collectors.toMap(
                        director -> director.getName(),
                        director -> director.getMovies().stream()
                                .mapToDouble(value -> value.getRating()).average().getAsDouble()
                )).entrySet().stream()
                        .sorted((o1, o2) -> Double.compare(o2.getValue(),o1.getValue()))
                                .collect(Collectors.toMap(
                                        stringDoubleEntry -> stringDoubleEntry.getKey(),
                                        stringDoubleEntry -> stringDoubleEntry.getValue()
                                ));

        collect.forEach(
                (s, aDouble) -> System.out.println(s+" :"+aDouble)
        );
    }
}
