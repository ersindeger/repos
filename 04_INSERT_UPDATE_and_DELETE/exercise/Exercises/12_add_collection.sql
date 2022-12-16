-- 12. Create a "Bill Murray Collection" in the collection table. For the
-- movies that have Bill Murray in them, set their collection ID to the
-- "Bill Murray Collection". (1 row, 6 rows)
INSERT into collection
(collection_name)
values
('Bill Murray Collection');


UPDATE movie
SET collection_id = (SELECT collection_id FROM collection
					 WHERE collection_name= 'Bill Murray Collection')
--all the films that Bill Murray played in were found first by finding his actor_ID,
--then by finding his movieIDs
WHERE movie_id = 399174 OR movie_id = 10315 OR movie_id = 120467
		OR movie_id = 83666 OR movie_id = 137 OR movie_id = 10433;
