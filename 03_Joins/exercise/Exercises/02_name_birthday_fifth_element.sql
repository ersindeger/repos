-- 2. The names and birthdays of actors in "The Fifth Element"
-- Order the results alphabetically (A-Z) by name.
-- (15 rows)

--SELECT *
--FROM movie
--WHERE title = 'The Fifth Element'; --identifies this movie as movie_id = 18, director_id = 59

SELECT person_name, birthday
FROM person
JOIN movie_actor ON person.person_id = movie_actor.actor_id
WHERE movie_actor.movie_id = (SELECT movie_id FROM movie WHERE title = 'The Fifth Element')
ORDER BY person_name ASC;
