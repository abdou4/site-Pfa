package com.streaming.controller;

import com.streaming.model.Episode;
import com.streaming.service.EpisodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/episodes")
public class EpisodeController {
    private final EpisodeService episodeService;

    public EpisodeController(EpisodeService episodeService) {
        this.episodeService = episodeService;
    }

    @GetMapping("/series/{seriesId}")
    public ResponseEntity<List<Episode>> getEpisodesBySeries(@PathVariable Long seriesId) {
        return ResponseEntity.ok(episodeService.getEpisodesBySeries(seriesId));
    }

    @GetMapping("/series/{seriesId}/season/{seasonNumber}")
    public ResponseEntity<List<Episode>> getEpisodesBySeason(
            @PathVariable Long seriesId,
            @PathVariable int seasonNumber) {
        return ResponseEntity.ok(episodeService.getEpisodesBySeason(seriesId, seasonNumber));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Episode> getEpisodeById(@PathVariable Long id) {
        return ResponseEntity.ok(episodeService.getEpisodeById(id));
    }

    @PostMapping
    public ResponseEntity<Episode> createEpisode(@RequestBody Episode episode) {
        return ResponseEntity.ok(episodeService.saveEpisode(episode));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Episode> updateEpisode(@PathVariable Long id, @RequestBody Episode episode) {
        episode.setId(id);
        return ResponseEntity.ok(episodeService.saveEpisode(episode));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEpisode(@PathVariable Long id) {
        episodeService.deleteEpisode(id);
        return ResponseEntity.ok().build();
    }
} 