package uz.najottalim.movieapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import uz.najottalim.movieapp.repos.DirectorRepo;
import uz.najottalim.movieapp.repos.GenreRepo;
import uz.najottalim.movieapp.repos.MovieRepo;


@Slf4j
public class AppCommandRunner implements CommandLineRunner {

    private DirectorRepo directorRepo;

    private GenreRepo genreRepo;

    private MovieRepo movieRepo;

    @Override
    public void run(String... args) throws Exception {
        log.info("Directors:");
        directorRepo.findAll()
                .forEach(c -> log.info(c.toString() + '\n'));

        log.info("Genres:");
        genreRepo.findAll()
                .forEach(o -> log.info(o.toString() + '\n'));

        log.info("Movies:");
        movieRepo.findAll()
                .forEach(p -> log.info(p.toString() + '\n'));
    }

}
