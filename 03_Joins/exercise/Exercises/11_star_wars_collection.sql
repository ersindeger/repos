-- 11. The titles of the movies in the "Star Wars Collection" ordered by release date, most recent first. 
-- (9 rows)

SELECT title
FROM movie
JOIN collection ON movie.collection_id = collection.collection_id
WHERE collection_name LIKE 'Star Wars Collection'
ORDER by release_date DESC;
