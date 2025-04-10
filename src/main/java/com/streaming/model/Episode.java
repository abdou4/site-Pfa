package com.streaming.model;

import com.streaming.entity.Movie;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "episodes")
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    private int seasonNumber;
    private int episodeNumber;
    private String title;
    private String description;
    private String videoUrl;
    private String thumbnailUrl;
    private String duration;
    private int viewCount = 0;
} 