package com.streaming.controller;

import com.streaming.entity.Movie;
import com.streaming.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.Collections;

@Controller
@RequestMapping("/series")
public class SeriesController {

    private final MovieService movieService;

    public SeriesController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public String showSeries(
            @RequestParam(name = "genre", required = false) String genre,
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "page", defaultValue = "0") int page,
            Model model) {
        try {
            List<Movie> allSeries = movieService.getAllSeries();
        
            if (genre != null && !genre.isEmpty()) {
                allSeries = allSeries.stream()
                        .filter(series -> series.getGenres().contains(genre))
                        .collect(Collectors.toList());
            }
            
            if (year != null) {
                allSeries = allSeries.stream()
                        .filter(series -> series.getReleaseYear() == year)
                        .collect(Collectors.toList());
            }
            
            if (sort != null) {
                switch (sort) {
                    case "rating":
                        allSeries.sort((a, b) -> Double.compare(b.getRating(), a.getRating()));
                        break;
                    case "year":
                        allSeries.sort((a, b) -> Integer.compare(b.getReleaseYear(), a.getReleaseYear()));
                        break;
                    case "title":
                        allSeries.sort((a, b) -> a.getTitle().compareTo(b.getTitle()));
                        break;
                }
            }
            
            Set<String> genres = new TreeSet<>();
            Set<Integer> years = new TreeSet<>();
            
            for (Movie series : allSeries) {
                genres.addAll(series.getGenres());
                years.add(series.getReleaseYear());
            }
            
            int pageSize = 12; 
            int totalSeries = allSeries.size();
            int totalPages = (int) Math.ceil((double) totalSeries / pageSize);
            
            if (page >= totalPages) {
                page = Math.max(0, totalPages - 1);
            }
            
            int start = page * pageSize;
            int end = Math.min(start + pageSize, totalSeries);
            
            List<Movie> paginatedSeries = 
                    start < totalSeries ? allSeries.subList(start, end) : List.of();
            
            model.addAttribute("seriesList", paginatedSeries);
            model.addAttribute("genres", genres);
            model.addAttribute("years", years);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", totalPages);
            
            return "series";
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement des séries : " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Une erreur est survenue lors du chargement des séries: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/{id}")
    public String showSeriesDetails(@PathVariable Long id, Model model) {
        try {
            Movie series = movieService.getMovieById(id);
            
            if (!series.isSeries()) {
                return "redirect:/movies/" + id;
            }
            
            List<Movie> allSeries = movieService.getAllSeries();
            
            List<Movie> similarSeries = allSeries.stream()
                    .filter(s -> s.getId() != id) 
                    .filter(s -> !Collections.disjoint(s.getGenres(), series.getGenres())) 
                    .sorted((a, b) -> Double.compare(b.getRating(), a.getRating())) 
                    .limit(4) 
                    .collect(Collectors.toList());
            
            model.addAttribute("series", series);
            model.addAttribute("similarSeries", similarSeries);
            return "series-details";
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement de la série : " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Une erreur est survenue lors du chargement de la série: " + e.getMessage());
            return "error";
        }
    }
} 