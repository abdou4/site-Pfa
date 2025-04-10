package com.streaming.controller;

import com.streaming.entity.Movie;
import com.streaming.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class SearchController {
    private final MovieService movieService;

    public SearchController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/search")
    public String search(
            @RequestParam(name = "q") String query,
            @RequestParam(name = "genre", required = false) String genre,
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "sort", required = false) String sort,
            Model model) {
        List<Movie> movies = movieService.searchMovies(query);
        
        if (genre != null && !genre.isEmpty()) {
            movies = movies.stream()
                    .filter(movie -> movie.getGenres().contains(genre))
                    .collect(Collectors.toList());
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

        List<MovieController.GenreDTO> genres = genreCounts.entrySet().stream()
            .map(entry -> new MovieController.GenreDTO(entry.getKey(), entry.getValue()))
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
        model.addAttribute("selectedGenre", genre);
        model.addAttribute("selectedYear", year);
        model.addAttribute("selectedSort", sort);
        
        return "search-results";
    }
}