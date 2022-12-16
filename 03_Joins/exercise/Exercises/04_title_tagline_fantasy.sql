-- 4. The titles and taglines of all the movies that are in the Fantasy genre. 
-- Order the results by title (A-Z).
-- (81 rows)

SELECT movie.title, movie.tagline
FROM movie
JOIN movie_genre ON movie.movie_id = movie_genre.movie_id
JOIN genre ON movie_genre.genre_id = genre.genre_id
WHERE genre.genre_name LIKE '%Fantasy%'
ORDER by title ASC
