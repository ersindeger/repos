-- 17. The titles and taglines of movies that are in the "Family" genre that Samuel L. Jackson has acted in.
-- Order the results alphabetically by movie title.
-- (4 rows)

SELECT title, tagline
FROM movie
JOIN movie_actor ON movie_actor.movie_id = movie.movie_id
JOIN person ON person.person_id = movie_actor.actor_id
JOIN movie_genre ON movie_genre.movie_id = movie.movie_id
JOIN genre ON movie_genre.genre_id = genre.genre_id
WHERE genre_name LIKE 'Family' AND person_name LIKE 'Samuel L. Jackson'
ORDER BY title ASC;
