package com.streaming.controller;

import com.streaming.entity.Movie;
import com.streaming.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private final MovieService movieService;

    public HomeController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Movie> featuredMovies = movieService.getFeaturedMovies();
        List<Movie> trendingMovies = movieService.getTrendingMovies();
        
        model.addAttribute("featuredMovies", featuredMovies);
        model.addAttribute("trendingMovies", trendingMovies);
        
        return "index";
    }
} 