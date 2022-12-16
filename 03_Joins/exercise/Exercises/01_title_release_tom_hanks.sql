-- 1. The titles and release dates of movies that Tom Hanks has appeared in. 
-- Order the results by release date, newest to oldest.
-- (47 rows)

SELECT title, release_date
FROM movie_actor
JOIN movie ON movie_actor.movie_id = movie.movie_id
WHERE movie_actor.actor_id = 31
ORDER BY release_date DESC;

-- first had to run the query :
--   SELECT person_name, person_id, movie_id, actor_id
--   FROM person
--   JOIN movie_actor ON person.person_id = movie_actor.actor_id
--   WHERE person_name = 'Tom Hanks'
--  to find out that Tom Hanks was actor_id = 31;
