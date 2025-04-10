package com.streaming.repository;

import com.streaming.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByFeaturedTrue();
    List<Movie> findByTrendingTrue();
    List<Movie> findByNewReleaseTrue();
    
    @Query("SELECT m FROM Movie m WHERE m.series = false")
    List<Movie> findAllMovies();
    
    @Query("SELECT m FROM Movie m WHERE m.series = true")
    List<Movie> findAllSeries();
    
    List<Movie> findByGenresContaining(String genre);
    
    List<Movie> findByTitleContainingIgnoreCase(String title);
} 