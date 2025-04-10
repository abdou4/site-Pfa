package com.streaming.service;

import com.streaming.model.Episode;
import com.streaming.repository.EpisodeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EpisodeService {
    private final EpisodeRepository episodeRepository;

    public EpisodeService(EpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }

    public List<Episode> getEpisodesBySeries(Long seriesId) {
        return episodeRepository.findByMovieId(seriesId);
    }

    public List<Episode> getEpisodesBySeason(Long seriesId, int seasonNumber) {
        return episodeRepository.findByMovieIdAndSeasonNumber(seriesId, seasonNumber);
    }

    public Episode getEpisodeById(Long id) {
        return episodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Episode not found"));
    }

    public Episode saveEpisode(Episode episode) {
        return episodeRepository.save(episode);
    }

    public void deleteEpisode(Long id) {
        episodeRepository.deleteById(id);
    }
} 