package com.streaming.controller;

import com.streaming.entity.Movie;
import com.streaming.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public String showMovies(
            @RequestParam(name = "genre", required = false) String genre,
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "page", defaultValue = "0") int page,
            Model model) {
        List<Movie> movies = movieService.getAllMovies();
        
        if (genre != null && !genre.isEmpty()) {
            movies = movieService.getMoviesByGenre(genre);
        }
        
        if (year != null) {
            movies = movies.stream()
                    .filter(movie -> movie.getReleaseYear() == year)
                    .collect(Collectors.toList());
        }
        
        if (sort != null) {
            switch (sort) {
                case "rating":
                    movies.sort((a, b) -> Double.compare(b.getRating(), a.getRating()));
                    break;
                case "year":
                    movies.sort((a, b) -> Integer.compare(b.getReleaseYear(), a.getReleaseYear()));
                    break;
                case "title":
                    movies.sort((a, b) -> a.getTitle().compareTo(b.getTitle()));
                    break;
            }
        }
        
        Map<String, Long> genreCounts = movies.stream()
            .flatMap(movie -> movie.getGenres().stream())
            .collect(Collectors.groupingBy(
                genreName -> genreName,
                Collectors.counting()
            ));

        List<GenreDTO> genres = genreCounts.entrySet().stream()
            .map(entry -> new GenreDTO(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
        
        Set<Integer> years = movies.stream()
            .map(Movie::getReleaseYear)
            .collect(Collectors.toCollection(TreeSet::new));
        
       
        int pageSize = 12; 
        int totalMovies = movies.size();
        int totalPages = (int) Math.ceil((double) totalMovies / pageSize);
        
        int start = page * pageSize;
        int end = Math.min(start + pageSize, totalMovies);
        List<Movie> pagedMovies = start < totalMovies ? movies.subList(start, end) : List.of();

        model.addAttribute("genres", genres);
        model.addAttribute("years", years);
        model.addAttribute("movies", pagedMovies);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("selectedGenre", genre);
        model.addAttribute("selectedYear", year);
        model.addAttribute("selectedSort", sort);
        
        return "movies";
    }

    @GetMapping("/{id}")
    public String showMovieDetails(@PathVariable Long id, Model model) {
        Movie movie = movieService.getMovieById(id);
        model.addAttribute("movie", movie);
        return "movie-details";
    }

    @GetMapping("/search")
    public String searchMovies(@RequestParam(name = "q") String query, Model model) {
        List<Movie> movies = movieService.searchMovies(query);
        
        Map<String, Long> genreCounts = movies.stream()
            .flatMap(movie -> movie.getGenres().stream())
            .collect(Collectors.groupingBy(
                genreName -> genreName,
                Collectors.counting()
            ));

        List<GenreDTO> genres = genreCounts.entrySet().stream()
            .map(entry -> new GenreDTO(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
        
        Set<Integer> years = movies.stream()
            .map(Movie::getReleaseYear)
            .collect(Collectors.toCollection(TreeSet::new));
        
        model.addAttribute("genres", genres);
        model.addAttribute("years", years);
        model.addAttribute("movies", movies);
        model.addAttribute("searchQuery", query);
        model.addAttribute("totalPages", 1); 
        model.addAttribute("currentPage", 0);
        
        return "movies";
    }

    public static class GenreDTO {
        private String name;
        private Long count;

        public GenreDTO(String name, Long count) {
            this.name = name;
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public Long getCount() {
            return count;
        }
    }
}