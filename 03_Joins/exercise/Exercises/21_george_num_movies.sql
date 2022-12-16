-- 21. For every person in the person table with the first name of "George", list the number of movies they've been in. Name the count column 'num_of_movies'.
-- Include all Georges, even those that have not appeared in any movies. Display the names in alphabetical order. 
-- (59 rows)

SELECT person_name, COUNT(*) as num_of_movies
FROM person
JOIN movie_actor ON movie_actor.actor_id = person.person_id
JOIN movie ON movie.movie_id = movie_actor.movie_id
WHERE person_name LIKE 'George %' OR ( movie.director_id = person.person_id AND person_name LIKE 'George %' )
GROUP by person_name
ORDER by person_name ASC;
