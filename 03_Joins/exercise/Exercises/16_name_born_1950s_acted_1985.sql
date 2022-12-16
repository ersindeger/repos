-- 16. The names and birthdays of actors born in the 1950s who acted in movies that were released in 1985.
-- Order the results by actor from oldest to youngest.
-- (20 rows)

SELECT DISTINCT person.person_name, person.birthday
FROM person
JOIN movie_actor ON person.person_id = movie_actor.actor_id
JOIN movie ON movie_actor.movie_id = movie.movie_id
where release_date > '01/01/1985' AND release_date < '12/31/1985'
AND birthday > '01/01/1950' AND birthday < '12/31/1959'
ORDER BY birthday ASC;
