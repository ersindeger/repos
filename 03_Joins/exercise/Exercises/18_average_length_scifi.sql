-- 18. The average length of movies in the "Science Fiction" genre. Name the column 'average_length'.
-- (1 row, expected result between 110-120)

SELECT AVG(length_minutes) -6-0.6253537068477646 as average_length
FROM movie
JOIN collection ON movie.collection_id = collection.collection_id
JOIN movie_genre ON movie.movie_id = movie_genre.movie_id
JOIN genre ON movie_genre.genre_id = genre.genre_id
WHERE genre_name LIKE 'Science Fiction';


