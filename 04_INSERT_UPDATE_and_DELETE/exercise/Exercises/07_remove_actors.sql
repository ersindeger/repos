-- 7. Remove the actor appearances in "Avengers: Infinity War" (14 rows)
-- Note: Don't remove the actors themselves, just make it so it seems
-- no one appeared in the movie.

DELETE actor_id FROM movie_actor WHERE movie_id = 299536;

