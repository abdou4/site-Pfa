package com.streaming.service;

import com.streaming.entity.Movie;
import com.streaming.repository.MovieRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        try {
            System.out.println("Service: Tentative de récupération de tous les films");
            List<Movie> movies = movieRepository.findAllMovies();
            System.out.println("Service: " + movies.size() + " films trouvés");
            return movies;
        } catch (Exception e) {
            System.err.println("Service: Erreur lors de la récupération des films");
            System.err.println("Service: Message d'erreur - " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Service: Cause - " + e.getCause().getMessage());
                e.getCause().printStackTrace();
            }
            throw new RuntimeException("Erreur lors de la récupération des films", e);
        }
    }

    public List<Movie> getFeaturedMovies() {
        return movieRepository.findByFeaturedTrue();
    }

    public List<Movie> getTrendingMovies() {
        return movieRepository.findByTrendingTrue();
    }

    public List<Movie> getNewReleases() {
        return movieRepository.findByNewReleaseTrue();
    }

    public List<Movie> getAllSeries() {
        try {
            System.out.println("Service: Tentative de récupération de toutes les séries");
            List<Movie> series = movieRepository.findAllSeries();
            System.out.println("Service: " + series.size() + " séries trouvées");
            return series;
        } catch (Exception e) {
            System.err.println("Service: Erreur lors de la récupération des séries");
            System.err.println("Service: Message d'erreur - " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Service: Cause - " + e.getCause().getMessage());
                e.getCause().printStackTrace();
            }
            throw new RuntimeException("Erreur lors de la récupération des séries", e);
        }
    }

    public List<Movie> searchMovies(String query) {
        return movieRepository.findByTitleContainingIgnoreCase(query);
    }

    public List<Movie> getMoviesByGenre(String genre) {
        return movieRepository.findByGenresContaining(genre);
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Film non trouvé avec l'ID: " + id));
    }

    public Movie saveMovie(Movie movie) {
        try {
            System.out.println("Service: Tentative de sauvegarde du film " + movie.getTitle());
            Movie savedMovie = movieRepository.save(movie);
            System.out.println("Service: Film sauvegardé avec succès, ID: " + savedMovie.getId());
            return savedMovie;
        } catch (Exception e) {
            System.err.println("Service: Erreur lors de la sauvegarde du film");
            System.err.println("Service: Message d'erreur - " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Service: Cause - " + e.getCause().getMessage());
                e.getCause().printStackTrace();
            }
            throw new RuntimeException("Erreur lors de la sauvegarde du film", e);
        }
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
} 