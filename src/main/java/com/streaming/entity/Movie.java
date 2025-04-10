package com.streaming.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "poster_url")
    private String posterUrl;

    private boolean series;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    private Double rating;

    private boolean featured;
    private boolean trending;
    
    @Column(name = "new_release")
    private boolean newRelease;

    @ElementCollection
    @CollectionTable(name = "movie_genres", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "genre")
    private List<String> genres;
    
    @Column(name = "seasons_count")
    private Integer seasonsCount;
    
    @Column(name = "episodes_count")
    private Integer episodesCount;
    
    @ElementCollection
    @CollectionTable(name = "series_trailers", joinColumns = @JoinColumn(name = "series_id"))
    private List<Trailer> trailers = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "series_episode_images", joinColumns = @JoinColumn(name = "series_id"))
    @Column(name = "image_url")
    private List<String> episodeImages = new ArrayList<>();
    
    @Embeddable
    public static class Trailer {
        private String title;
        
        @Column(name = "youtube_id")
        private String youtubeId;
        
        public String getTitle() {
            return title;
        }
        
        public void setTitle(String title) {
            this.title = title;
        }
        
        public String getYoutubeId() {
            return youtubeId;
        }
        
        public void setYoutubeId(String youtubeId) {
            this.youtubeId = youtubeId;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public boolean isSeries() {
        return series;
    }

    public void setSeries(boolean series) {
        this.series = series;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public boolean isTrending() {
        return trending;
    }

    public void setTrending(boolean trending) {
        this.trending = trending;
    }

    public boolean isNewRelease() {
        return newRelease;
    }

    public void setNewRelease(boolean newRelease) {
        this.newRelease = newRelease;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
    
    public Integer getSeasonsCount() {
        return seasonsCount;
    }
    
    public void setSeasonsCount(Integer seasonsCount) {
        this.seasonsCount = seasonsCount;
    }
    
    public Integer getEpisodesCount() {
        return episodesCount;
    }
    
    public void setEpisodesCount(Integer episodesCount) {
        this.episodesCount = episodesCount;
    }
    
    public List<Trailer> getTrailers() {
        return trailers;
    }
    
    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }
    
    public List<String> getEpisodeImages() {
        return episodeImages;
    }
    
    public void setEpisodeImages(List<String> episodeImages) {
        this.episodeImages = episodeImages;
    }
} 