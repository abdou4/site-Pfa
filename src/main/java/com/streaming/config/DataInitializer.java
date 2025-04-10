package com.streaming.config;

import com.streaming.entity.Movie;
import com.streaming.entity.Movie.Trailer;
import com.streaming.service.MovieService;
import com.streaming.model.Role;
import com.streaming.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final MovieService movieService;
    private final RoleRepository roleRepository;

    public DataInitializer(MovieService movieService, RoleRepository roleRepository) {
        this.movieService = movieService;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        System.out.println("Début de l'initialisation des données...");
        
       
        if (roleRepository.findByName("ROLE_USER").isEmpty()) {
            Role userRole = new Role("ROLE_USER");
            roleRepository.save(userRole);
            System.out.println("Rôle ROLE_USER créé");
        }

        try {
            List<Movie> existingSeries = movieService.getAllSeries();
            if (existingSeries.isEmpty()) {
                System.out.println("Aucune série trouvée, initialisation des séries...");
                
                Movie series1 = new Movie();
                series1.setTitle("Breaking Bad");
                series1.setDescription("A high school chemistry teacher diagnosed with inoperable lung cancer turns to manufacturing and selling methamphetamine in order to secure his family's future.");
                List<String> genres1 = new ArrayList<>();
                genres1.add("Drama");
                genres1.add("Crime");
                series1.setGenres(genres1);
                series1.setPosterUrl("https://m.media-amazon.com/images/M/MV5BMTJiMzgwZTktYzZhZC00YzhhLWEzZDUtMGM2NTE4MzQ4NGFmXkEyXkFqcGdeQWpybA@@._V1_.jpg");
                series1.setSeries(true);
                series1.setReleaseYear(2008);
                series1.setDurationMinutes(45);
                series1.setRating(9.5);
                series1.setFeatured(true);
                series1.setTrending(true);
                
                series1.setSeasonsCount(5);
                series1.setEpisodesCount(62);
                
                Trailer trailer1 = new Trailer();
                trailer1.setTitle("Breaking Bad Trailer");
                trailer1.setYoutubeId("HhesaQXLuRY");
                series1.getTrailers().add(trailer1);
                
                series1.setEpisodeImages(Arrays.asList(
                    "https://m.media-amazon.com/images/M/MV5BMjhiMzgxZTctNDc1Ni00OTIxLTlhMTYtZTA3ZWFkODRkNmE2XkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_UY1200_CR90,0,630,1200_AL_.jpg",
                    "https://m.media-amazon.com/images/M/MV5BNDkyZThhNmMtZDBjYS00NDBmLTlkMjgtNWM2ZWYzZDQxZWU1XkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg",
                    "https://m.media-amazon.com/images/M/MV5BMTkxMTQzMTA5MF5BMl5BanBnXkFtZTcwMDU3MTI3OA@@._V1_.jpg"
                ));
                
                movieService.saveMovie(series1);
                System.out.println("Série créée : " + series1.getTitle());

                Movie series2 = new Movie();
                series2.setTitle("Game of Thrones");
                series2.setDescription("Nine noble families fight for control over the lands of Westeros, while an ancient enemy returns after being dormant for millennia.");
                List<String> genres2 = new ArrayList<>();
                genres2.add("Fantasy");
                genres2.add("Drama");
                series2.setGenres(genres2);
                series2.setPosterUrl("https://m.media-amazon.com/images/M/MV5BYTRiNDQwYzAtMzVlZS00NTI5LWJjYjUtMzkwNTUzMWMxZTllXkEyXkFqcGdeQXVyNDIzMzcwNjc@._V1_.jpg");
                series2.setSeries(true);
                series2.setReleaseYear(2011);
                series2.setDurationMinutes(60);
                series2.setRating(9.3);
                series2.setFeatured(true);
                series2.setTrending(true);
                
                series2.setSeasonsCount(8);
                series2.setEpisodesCount(73);
                
                Trailer trailer2 = new Trailer();
                trailer2.setTitle("Game of Thrones Saison 1 Trailer");
                trailer2.setYoutubeId("KPLWWIOCOOQ");
                series2.getTrailers().add(trailer2);
                
                series2.setEpisodeImages(Arrays.asList(
                    "https://m.media-amazon.com/images/M/MV5BZTg4YzdjODgtMmZkNC00OGY0LWI3ODQtMzJmZThjMzI5NjZkXkEyXkFqcGdeQXVyNDIwOTkyNjM@._V1_.jpg",
                    "https://m.media-amazon.com/images/M/MV5BMjA5NzA5NjMwNl5BMl5BanBnXkFtZTgwNjg2OTk2NzM@._V1_.jpg",
                    "https://m.media-amazon.com/images/M/MV5BNzQ1MmJjZDUtMmI5OC00ZDk2LThkODQtODgwYmU0MTIzNDhmXkEyXkFqcGdeQXVyNjQ3NTcxMDg@._V1_.jpg"
                ));
                
                movieService.saveMovie(series2);
                System.out.println("Série créée : " + series2.getTitle());

                Movie series3 = new Movie();
                series3.setTitle("Stranger Things");
                series3.setDescription("When a young boy disappears, his mother, a police chief and his friends must confront terrifying supernatural forces in order to get him back.");
                List<String> genres3 = new ArrayList<>();
                genres3.add("Science Fiction");
                genres3.add("Horror");
                series3.setGenres(genres3);
                series3.setPosterUrl("https://m.media-amazon.com/images/M/MV5BMDZkYmVhNjMtNWU4MC00MDQxLWE3MjYtZGMzZWI1ZjhlOWJmXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_.jpg");
                series3.setSeries(true);
                series3.setReleaseYear(2016);
                series3.setDurationMinutes(50);
                series3.setRating(8.7);
                series3.setFeatured(true);
                series3.setTrending(true);
                
                series3.setSeasonsCount(4);
                series3.setEpisodesCount(34);
                
                Trailer trailer3 = new Trailer();
                trailer3.setTitle("Stranger Things Saison 1 Trailer");
                trailer3.setYoutubeId("b9EkMc79ZSU");
                series3.getTrailers().add(trailer3);
                
                series3.setEpisodeImages(Arrays.asList(
                    "https://m.media-amazon.com/images/M/MV5BMTUzNTU0ODM2OF5BMl5BanBnXkFtZTgwODIwOTY0OTE@._V1_.jpg",
                    "https://m.media-amazon.com/images/M/MV5BMjEzNjM2MDI4NF5BMl5BanBnXkFtZTgwNzEwOTY0OTE@._V1_.jpg",
                    "https://m.media-amazon.com/images/M/MV5BMjA5OTM5MjEwN15BMl5BanBnXkFtZTgwMjkwOTY0OTE@._V1_.jpg"
                ));
                
                movieService.saveMovie(series3);
                System.out.println("Série créée : " + series3.getTitle());
            } else {
                System.out.println(existingSeries.size() + " séries trouvées dans la base de données");
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de l'initialisation des séries : " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Initialisation des données terminée avec succès !");
    }
}