package com.streaming.repository;

import com.streaming.model.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
    List<Episode> findByMovieId(Long movieId);
    List<Episode> findByMovieIdAndSeasonNumber(Long movieId, int seasonNumber);
} 