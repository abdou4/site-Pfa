
CREATE TABLE IF NOT EXISTS movies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    poster_url VARCHAR(255),
    series BOOLEAN DEFAULT FALSE,
    release_year INT,
    duration_minutes INT,
    rating DECIMAL(3,1),
    featured BOOLEAN DEFAULT FALSE,
    trending BOOLEAN DEFAULT FALSE,
    new_release BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS movie_genres (
    movie_id BIGINT,
    genre VARCHAR(50),
    FOREIGN KEY (movie_id) REFERENCES movies(id)
);

INSERT INTO movies (title, description, poster_url, series, release_year, duration_minutes, rating, featured, trending, new_release) VALUES
('Inception', 'Un voleur qui s''infiltre dans les rêves des autres est chargé de planter une idée dans l''esprit d''un PDG.', 'https://image.tmdb.org/t/p/w500/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg', false, 2010, 148, 8.8, true, true, false),
('The Dark Knight', 'Batman affronte le Joker, un criminel psychotique qui sème le chaos à Gotham City.', 'https://image.tmdb.org/t/p/w500/qJ2tW6WM0uxq66dT8wPv0bqHPd1.jpg', false, 2008, 152, 9.0, true, true, false),
('Interstellar', 'Une équipe d''explorateurs voyage à travers un trou de ver dans l''espace pour sauver l''humanité.', 'https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg', false, 2014, 169, 8.6, true, true, false),
('Pulp Fiction', 'Les vies de deux tueurs à gages, d''un boxeur, d''un gangster et de sa femme s''entrecroisent.', 'https://image.tmdb.org/t/p/w500/d5iIlFn5ol0F1jNkKS3b0Zq2B6x.jpg', false, 1994, 154, 8.9, true, true, false),
('The Matrix', 'Un hacker découvre que la réalité n''est qu''une simulation créée par des machines intelligentes.', 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg', false, 1999, 136, 8.7, true, true, false);

INSERT INTO movies (title, description, poster_url, series, release_year, duration_minutes, rating, featured, trending, new_release) VALUES
('Breaking Bad', 'Un professeur de chimie atteint d''un cancer se lance dans la fabrication de méthamphétamine.', 'https://image.tmdb.org/t/p/w500/ggFHVNu6YYIhLgmWNiQ1g3HXbV.jpg', true, 2008, 45, 9.5, true, true, false),
('Game of Thrones', 'Plusieurs familles nobles se battent pour le contrôle du Trône de Fer dans le royaume de Westeros.', 'https://image.tmdb.org/t/p/w500/u3bZgnGQ9T01sWNhyveQz0wH0Hl.jpg', true, 2011, 60, 8.8, true, true, false),
('Stranger Things', 'Un jeune garçon disparaît mystérieusement, et ses amis découvrent des phénomènes surnaturels.', 'https://image.tmdb.org/t/p/w500/49WJfeN0moxb9IPfGn8AIqMGskD.jpg', true, 2016, 50, 8.7, true, true, false),
('The Mandalorian', 'Un chasseur de primes solitaire navigue dans les confins de la galaxie.', 'https://image.tmdb.org/t/p/w500/eOG4QftB7XxcYxL1ECMsiYrZnXy.jpg', true, 2019, 40, 8.7, true, true, false),
('The Witcher', 'Un chasseur de monstres mutant parcourt le monde à la recherche de sa place dans la vie.', 'https://image.tmdb.org/t/p/w500/7vjaCdMw15FEbXyLQTVa04URsPm.jpg', true, 2019, 60, 8.2, true, true, false);

INSERT INTO movie_genres (movie_id, genre) VALUES
(1, 'Science-Fiction'), (1, 'Action'), (1, 'Thriller'),
(2, 'Action'), (2, 'Crime'), (2, 'Drame'),
(3, 'Science-Fiction'), (3, 'Aventure'), (3, 'Drame'),
(4, 'Crime'), (4, 'Drame'),
(5, 'Science-Fiction'), (5, 'Action');

INSERT INTO movie_genres (movie_id, genre) VALUES
(6, 'Drame'), (6, 'Crime'), (6, 'Thriller'),
(7, 'Fantasy'), (7, 'Aventure'), (7, 'Drame'),
(8, 'Science-Fiction'), (8, 'Horreur'), (8, 'Mystère'),
(9, 'Science-Fiction'), (9, 'Aventure'), (9, 'Action'),
(10, 'Fantasy'), (10, 'Aventure'), (10, 'Action'); 