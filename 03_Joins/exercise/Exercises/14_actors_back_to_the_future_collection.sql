-- 14. The names of actors who've appeared in the movies in the "Back to the Future Collection", sorted alphabetically.
-- (28 rows)

SELECT DISTINCT person_name
FROM person
JOIN movie_actor ON movie_actor.actor_id = person.person_id
JOIN movie ON movie_actor.movie_id = movie.movie_id
JOIN collection ON collection.collection_id = movie.collection_id
WHERE collection.collection_name LIKE 'Back to the Future Collection'
ORDER by person_name ASC;
